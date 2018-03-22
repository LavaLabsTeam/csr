package com.lavalabs.csr.web.rest;

import com.lavalabs.csr.CsrApp;

import com.lavalabs.csr.domain.PackageOrder;
import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.repository.PackageOrderRepository;
import com.lavalabs.csr.service.PackageOrderService;
import com.lavalabs.csr.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lavalabs.csr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PackageOrderResource REST controller.
 *
 * @see PackageOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsrApp.class)
public class PackageOrderResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private PackageOrderRepository packageOrderRepository;

    @Autowired
    private PackageOrderService packageOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPackageOrderMockMvc;

    private PackageOrder packageOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackageOrderResource packageOrderResource = new PackageOrderResource(packageOrderService);
        this.restPackageOrderMockMvc = MockMvcBuilders.standaloneSetup(packageOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageOrder createEntity(EntityManager em) {
        PackageOrder packageOrder = new PackageOrder()
            .quantity(DEFAULT_QUANTITY)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .message(DEFAULT_MESSAGE);
        // Add required entity
        MerchantPackage merchantPackage = MerchantPackageResourceIntTest.createEntity(em);
        em.persist(merchantPackage);
        em.flush();
        packageOrder.setMerchantPackage(merchantPackage);
        return packageOrder;
    }

    @Before
    public void initTest() {
        packageOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackageOrder() throws Exception {
        int databaseSizeBeforeCreate = packageOrderRepository.findAll().size();

        // Create the PackageOrder
        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isCreated());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PackageOrder testPackageOrder = packageOrderList.get(packageOrderList.size() - 1);
        assertThat(testPackageOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPackageOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPackageOrder.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPackageOrder.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPackageOrder.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createPackageOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packageOrderRepository.findAll().size();

        // Create the PackageOrder with an existing ID
        packageOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setQuantity(null);

        // Create the PackageOrder, which fails.

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setName(null);

        // Create the PackageOrder, which fails.

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setEmail(null);

        // Create the PackageOrder, which fails.

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageOrderRepository.findAll().size();
        // set the field null
        packageOrder.setPhone(null);

        // Create the PackageOrder, which fails.

        restPackageOrderMockMvc.perform(post("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isBadRequest());

        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackageOrders() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);

        // Get all the packageOrderList
        restPackageOrderMockMvc.perform(get("/api/package-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }

    @Test
    @Transactional
    public void getPackageOrder() throws Exception {
        // Initialize the database
        packageOrderRepository.saveAndFlush(packageOrder);

        // Get the packageOrder
        restPackageOrderMockMvc.perform(get("/api/package-orders/{id}", packageOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(packageOrder.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackageOrder() throws Exception {
        // Get the packageOrder
        restPackageOrderMockMvc.perform(get("/api/package-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageOrder() throws Exception {
        // Initialize the database
        packageOrderService.save(packageOrder);

        int databaseSizeBeforeUpdate = packageOrderRepository.findAll().size();

        // Update the packageOrder
        PackageOrder updatedPackageOrder = packageOrderRepository.findOne(packageOrder.getId());
        // Disconnect from session so that the updates on updatedPackageOrder are not directly saved in db
        em.detach(updatedPackageOrder);
        updatedPackageOrder
            .quantity(UPDATED_QUANTITY)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .message(UPDATED_MESSAGE);

        restPackageOrderMockMvc.perform(put("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPackageOrder)))
            .andExpect(status().isOk());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeUpdate);
        PackageOrder testPackageOrder = packageOrderList.get(packageOrderList.size() - 1);
        assertThat(testPackageOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPackageOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPackageOrder.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPackageOrder.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPackageOrder.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingPackageOrder() throws Exception {
        int databaseSizeBeforeUpdate = packageOrderRepository.findAll().size();

        // Create the PackageOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPackageOrderMockMvc.perform(put("/api/package-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageOrder)))
            .andExpect(status().isCreated());

        // Validate the PackageOrder in the database
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePackageOrder() throws Exception {
        // Initialize the database
        packageOrderService.save(packageOrder);

        int databaseSizeBeforeDelete = packageOrderRepository.findAll().size();

        // Get the packageOrder
        restPackageOrderMockMvc.perform(delete("/api/package-orders/{id}", packageOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PackageOrder> packageOrderList = packageOrderRepository.findAll();
        assertThat(packageOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageOrder.class);
        PackageOrder packageOrder1 = new PackageOrder();
        packageOrder1.setId(1L);
        PackageOrder packageOrder2 = new PackageOrder();
        packageOrder2.setId(packageOrder1.getId());
        assertThat(packageOrder1).isEqualTo(packageOrder2);
        packageOrder2.setId(2L);
        assertThat(packageOrder1).isNotEqualTo(packageOrder2);
        packageOrder1.setId(null);
        assertThat(packageOrder1).isNotEqualTo(packageOrder2);
    }
}
