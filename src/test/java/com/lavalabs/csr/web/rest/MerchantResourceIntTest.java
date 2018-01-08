package com.lavalabs.csr.web.rest;

import com.lavalabs.csr.CsrApp;

import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.repository.MerchantRepository;
import com.lavalabs.csr.repository.UserRepository;
import com.lavalabs.csr.service.MailService;
import com.lavalabs.csr.service.MerchantService;
import com.lavalabs.csr.service.UserService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lavalabs.csr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MerchantResource REST controller.
 *
 * @see MerchantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsrApp.class)
public class MerchantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantService merchantService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantMockMvc;

    private Merchant merchant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantResource merchantResource = new MerchantResource(merchantService, userRepository, userService, mailService);
        this.restMerchantMockMvc = MockMvcBuilders.standaloneSetup(merchantResource)
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
    public static Merchant createEntity(EntityManager em) {
        Merchant merchant = new Merchant()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return merchant;
    }

    @Before
    public void initTest() {
        merchant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchant() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant
        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMerchant.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testMerchant.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMerchant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMerchant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMerchant.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testMerchant.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMerchant.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMerchantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant with an existing ID
        merchant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantMockMvc.perform(post("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isBadRequest());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMerchants() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get all the merchantList
        restMerchantMockMvc.perform(get("/api/merchants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getMerchant() throws Exception {
        // Initialize the database
        merchantRepository.saveAndFlush(merchant);

        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", merchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMerchant() throws Exception {
        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchant() throws Exception {
        // Initialize the database
        merchantService.save(merchant);

        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant
        Merchant updatedMerchant = merchantRepository.findOne(merchant.getId());
        // Disconnect from session so that the updates on updatedMerchant are not directly saved in db
        em.detach(updatedMerchant);
        updatedMerchant
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restMerchantMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchant)))
            .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchantList.get(merchantList.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testMerchant.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMerchant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMerchant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMerchant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMerchant.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testMerchant.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMerchant.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchant() throws Exception {
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Create the Merchant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantMockMvc.perform(put("/api/merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchant)))
            .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchant() throws Exception {
        // Initialize the database
        merchantService.save(merchant);

        int databaseSizeBeforeDelete = merchantRepository.findAll().size();

        // Get the merchant
        restMerchantMockMvc.perform(delete("/api/merchants/{id}", merchant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Merchant> merchantList = merchantRepository.findAll();
        assertThat(merchantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Merchant.class);
        Merchant merchant1 = new Merchant();
        merchant1.setId(1L);
        Merchant merchant2 = new Merchant();
        merchant2.setId(merchant1.getId());
        assertThat(merchant1).isEqualTo(merchant2);
        merchant2.setId(2L);
        assertThat(merchant1).isNotEqualTo(merchant2);
        merchant1.setId(null);
        assertThat(merchant1).isNotEqualTo(merchant2);
    }
}
