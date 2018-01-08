package com.lavalabs.csr.web.rest;

import com.lavalabs.csr.CsrApp;

import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.repository.MerchantPackageRepository;
import com.lavalabs.csr.service.MerchantPackageService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lavalabs.csr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MerchantPackageResource REST controller.
 *
 * @see MerchantPackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsrApp.class)
public class MerchantPackageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_INCLUDES = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_INCLUDES = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_AND_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_AND_CONDITION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Double DEFAULT_OLD_PRICE = 1D;
    private static final Double UPDATED_OLD_PRICE = 2D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MerchantPackageRepository merchantPackageRepository;

    @Autowired
    private MerchantPackageService merchantPackageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantPackageMockMvc;

    private MerchantPackage merchantPackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantPackageResource merchantPackageResource = new MerchantPackageResource(merchantPackageService);
        this.restMerchantPackageMockMvc = MockMvcBuilders.standaloneSetup(merchantPackageResource)
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
    public static MerchantPackage createEntity(EntityManager em) {
        MerchantPackage merchantPackage = new MerchantPackage()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .packageIncludes(DEFAULT_PACKAGE_INCLUDES)
            .termsAndCondition(DEFAULT_TERMS_AND_CONDITION)
            .price(DEFAULT_PRICE)
            .oldPrice(DEFAULT_OLD_PRICE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return merchantPackage;
    }

    @Before
    public void initTest() {
        merchantPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantPackage() throws Exception {
        int databaseSizeBeforeCreate = merchantPackageRepository.findAll().size();

        // Create the MerchantPackage
        restMerchantPackageMockMvc.perform(post("/api/merchant-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackage)))
            .andExpect(status().isCreated());

        // Validate the MerchantPackage in the database
        List<MerchantPackage> merchantPackageList = merchantPackageRepository.findAll();
        assertThat(merchantPackageList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantPackage testMerchantPackage = merchantPackageList.get(merchantPackageList.size() - 1);
        assertThat(testMerchantPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchantPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMerchantPackage.getPackageIncludes()).isEqualTo(DEFAULT_PACKAGE_INCLUDES);
        assertThat(testMerchantPackage.getTermsAndCondition()).isEqualTo(DEFAULT_TERMS_AND_CONDITION);
        assertThat(testMerchantPackage.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMerchantPackage.getOldPrice()).isEqualTo(DEFAULT_OLD_PRICE);
        assertThat(testMerchantPackage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMerchantPackage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMerchantPackage.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMerchantPackage.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMerchantPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantPackageRepository.findAll().size();

        // Create the MerchantPackage with an existing ID
        merchantPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantPackageMockMvc.perform(post("/api/merchant-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackage)))
            .andExpect(status().isBadRequest());

        // Validate the MerchantPackage in the database
        List<MerchantPackage> merchantPackageList = merchantPackageRepository.findAll();
        assertThat(merchantPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMerchantPackages() throws Exception {
        // Initialize the database
        merchantPackageRepository.saveAndFlush(merchantPackage);

        // Get all the merchantPackageList
        restMerchantPackageMockMvc.perform(get("/api/merchant-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].packageIncludes").value(hasItem(DEFAULT_PACKAGE_INCLUDES.toString())))
            .andExpect(jsonPath("$.[*].termsAndCondition").value(hasItem(DEFAULT_TERMS_AND_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].oldPrice").value(hasItem(DEFAULT_OLD_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getMerchantPackage() throws Exception {
        // Initialize the database
        merchantPackageRepository.saveAndFlush(merchantPackage);

        // Get the merchantPackage
        restMerchantPackageMockMvc.perform(get("/api/merchant-packages/{id}", merchantPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantPackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.packageIncludes").value(DEFAULT_PACKAGE_INCLUDES.toString()))
            .andExpect(jsonPath("$.termsAndCondition").value(DEFAULT_TERMS_AND_CONDITION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.oldPrice").value(DEFAULT_OLD_PRICE.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMerchantPackage() throws Exception {
        // Get the merchantPackage
        restMerchantPackageMockMvc.perform(get("/api/merchant-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantPackage() throws Exception {
        // Initialize the database
        merchantPackageService.save(merchantPackage);

        int databaseSizeBeforeUpdate = merchantPackageRepository.findAll().size();

        // Update the merchantPackage
        MerchantPackage updatedMerchantPackage = merchantPackageRepository.findOne(merchantPackage.getId());
        // Disconnect from session so that the updates on updatedMerchantPackage are not directly saved in db
        em.detach(updatedMerchantPackage);
        updatedMerchantPackage
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .packageIncludes(UPDATED_PACKAGE_INCLUDES)
            .termsAndCondition(UPDATED_TERMS_AND_CONDITION)
            .price(UPDATED_PRICE)
            .oldPrice(UPDATED_OLD_PRICE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restMerchantPackageMockMvc.perform(put("/api/merchant-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantPackage)))
            .andExpect(status().isOk());

        // Validate the MerchantPackage in the database
        List<MerchantPackage> merchantPackageList = merchantPackageRepository.findAll();
        assertThat(merchantPackageList).hasSize(databaseSizeBeforeUpdate);
        MerchantPackage testMerchantPackage = merchantPackageList.get(merchantPackageList.size() - 1);
        assertThat(testMerchantPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchantPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMerchantPackage.getPackageIncludes()).isEqualTo(UPDATED_PACKAGE_INCLUDES);
        assertThat(testMerchantPackage.getTermsAndCondition()).isEqualTo(UPDATED_TERMS_AND_CONDITION);
        assertThat(testMerchantPackage.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMerchantPackage.getOldPrice()).isEqualTo(UPDATED_OLD_PRICE);
        assertThat(testMerchantPackage.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMerchantPackage.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMerchantPackage.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMerchantPackage.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantPackage() throws Exception {
        int databaseSizeBeforeUpdate = merchantPackageRepository.findAll().size();

        // Create the MerchantPackage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantPackageMockMvc.perform(put("/api/merchant-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackage)))
            .andExpect(status().isCreated());

        // Validate the MerchantPackage in the database
        List<MerchantPackage> merchantPackageList = merchantPackageRepository.findAll();
        assertThat(merchantPackageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchantPackage() throws Exception {
        // Initialize the database
        merchantPackageService.save(merchantPackage);

        int databaseSizeBeforeDelete = merchantPackageRepository.findAll().size();

        // Get the merchantPackage
        restMerchantPackageMockMvc.perform(delete("/api/merchant-packages/{id}", merchantPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MerchantPackage> merchantPackageList = merchantPackageRepository.findAll();
        assertThat(merchantPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantPackage.class);
        MerchantPackage merchantPackage1 = new MerchantPackage();
        merchantPackage1.setId(1L);
        MerchantPackage merchantPackage2 = new MerchantPackage();
        merchantPackage2.setId(merchantPackage1.getId());
        assertThat(merchantPackage1).isEqualTo(merchantPackage2);
        merchantPackage2.setId(2L);
        assertThat(merchantPackage1).isNotEqualTo(merchantPackage2);
        merchantPackage1.setId(null);
        assertThat(merchantPackage1).isNotEqualTo(merchantPackage2);
    }
}
