package com.lavalabs.csr.web.rest;

import com.lavalabs.csr.CsrApp;

import com.lavalabs.csr.domain.MerchantPackagePhoto;
import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.repository.MerchantPackagePhotoRepository;
import com.lavalabs.csr.service.MerchantPackagePhotoService;
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
 * Test class for the MerchantPackagePhotoResource REST controller.
 *
 * @see MerchantPackagePhotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsrApp.class)
public class MerchantPackagePhotoResourceIntTest {

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MerchantPackagePhotoRepository merchantPackagePhotoRepository;

    @Autowired
    private MerchantPackagePhotoService merchantPackagePhotoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantPackagePhotoMockMvc;

    private MerchantPackagePhoto merchantPackagePhoto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MerchantPackagePhotoResource merchantPackagePhotoResource = new MerchantPackagePhotoResource(merchantPackagePhotoService);
        this.restMerchantPackagePhotoMockMvc = MockMvcBuilders.standaloneSetup(merchantPackagePhotoResource)
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
    public static MerchantPackagePhoto createEntity(EntityManager em) {
        MerchantPackagePhoto merchantPackagePhoto = new MerchantPackagePhoto()
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        // Add required entity
        MerchantPackage merchantPackage = MerchantPackageResourceIntTest.createEntity(em);
        em.persist(merchantPackage);
        em.flush();
        merchantPackagePhoto.setMerchantPackage(merchantPackage);
        return merchantPackagePhoto;
    }

    @Before
    public void initTest() {
        merchantPackagePhoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantPackagePhoto() throws Exception {
        int databaseSizeBeforeCreate = merchantPackagePhotoRepository.findAll().size();

        // Create the MerchantPackagePhoto
        restMerchantPackagePhotoMockMvc.perform(post("/api/merchant-package-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackagePhoto)))
            .andExpect(status().isCreated());

        // Validate the MerchantPackagePhoto in the database
        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantPackagePhoto testMerchantPackagePhoto = merchantPackagePhotoList.get(merchantPackagePhotoList.size() - 1);
        assertThat(testMerchantPackagePhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMerchantPackagePhoto.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMerchantPackagePhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantPackagePhotoRepository.findAll().size();

        // Create the MerchantPackagePhoto with an existing ID
        merchantPackagePhoto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantPackagePhotoMockMvc.perform(post("/api/merchant-package-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackagePhoto)))
            .andExpect(status().isBadRequest());

        // Validate the MerchantPackagePhoto in the database
        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantPackagePhotoRepository.findAll().size();
        // set the field null
        merchantPackagePhoto.setPhoto(null);

        // Create the MerchantPackagePhoto, which fails.

        restMerchantPackagePhotoMockMvc.perform(post("/api/merchant-package-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackagePhoto)))
            .andExpect(status().isBadRequest());

        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchantPackagePhotos() throws Exception {
        // Initialize the database
        merchantPackagePhotoRepository.saveAndFlush(merchantPackagePhoto);

        // Get all the merchantPackagePhotoList
        restMerchantPackagePhotoMockMvc.perform(get("/api/merchant-package-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantPackagePhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getMerchantPackagePhoto() throws Exception {
        // Initialize the database
        merchantPackagePhotoRepository.saveAndFlush(merchantPackagePhoto);

        // Get the merchantPackagePhoto
        restMerchantPackagePhotoMockMvc.perform(get("/api/merchant-package-photos/{id}", merchantPackagePhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantPackagePhoto.getId().intValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMerchantPackagePhoto() throws Exception {
        // Get the merchantPackagePhoto
        restMerchantPackagePhotoMockMvc.perform(get("/api/merchant-package-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantPackagePhoto() throws Exception {
        // Initialize the database
        merchantPackagePhotoService.save(merchantPackagePhoto);

        int databaseSizeBeforeUpdate = merchantPackagePhotoRepository.findAll().size();

        // Update the merchantPackagePhoto
        MerchantPackagePhoto updatedMerchantPackagePhoto = merchantPackagePhotoRepository.findOne(merchantPackagePhoto.getId());
        // Disconnect from session so that the updates on updatedMerchantPackagePhoto are not directly saved in db
        em.detach(updatedMerchantPackagePhoto);
        updatedMerchantPackagePhoto
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restMerchantPackagePhotoMockMvc.perform(put("/api/merchant-package-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantPackagePhoto)))
            .andExpect(status().isOk());

        // Validate the MerchantPackagePhoto in the database
        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeUpdate);
        MerchantPackagePhoto testMerchantPackagePhoto = merchantPackagePhotoList.get(merchantPackagePhotoList.size() - 1);
        assertThat(testMerchantPackagePhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMerchantPackagePhoto.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantPackagePhoto() throws Exception {
        int databaseSizeBeforeUpdate = merchantPackagePhotoRepository.findAll().size();

        // Create the MerchantPackagePhoto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantPackagePhotoMockMvc.perform(put("/api/merchant-package-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantPackagePhoto)))
            .andExpect(status().isCreated());

        // Validate the MerchantPackagePhoto in the database
        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchantPackagePhoto() throws Exception {
        // Initialize the database
        merchantPackagePhotoService.save(merchantPackagePhoto);

        int databaseSizeBeforeDelete = merchantPackagePhotoRepository.findAll().size();

        // Get the merchantPackagePhoto
        restMerchantPackagePhotoMockMvc.perform(delete("/api/merchant-package-photos/{id}", merchantPackagePhoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MerchantPackagePhoto> merchantPackagePhotoList = merchantPackagePhotoRepository.findAll();
        assertThat(merchantPackagePhotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantPackagePhoto.class);
        MerchantPackagePhoto merchantPackagePhoto1 = new MerchantPackagePhoto();
        merchantPackagePhoto1.setId(1L);
        MerchantPackagePhoto merchantPackagePhoto2 = new MerchantPackagePhoto();
        merchantPackagePhoto2.setId(merchantPackagePhoto1.getId());
        assertThat(merchantPackagePhoto1).isEqualTo(merchantPackagePhoto2);
        merchantPackagePhoto2.setId(2L);
        assertThat(merchantPackagePhoto1).isNotEqualTo(merchantPackagePhoto2);
        merchantPackagePhoto1.setId(null);
        assertThat(merchantPackagePhoto1).isNotEqualTo(merchantPackagePhoto2);
    }
}
