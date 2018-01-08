package com.lavalabs.csr.web.rest;

import com.lavalabs.csr.CsrApp;

import com.lavalabs.csr.domain.UserMerchant;
import com.lavalabs.csr.domain.User;
import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.repository.UserMerchantRepository;
import com.lavalabs.csr.service.UserMerchantService;
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
 * Test class for the UserMerchantResource REST controller.
 *
 * @see UserMerchantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsrApp.class)
public class UserMerchantResourceIntTest {

    @Autowired
    private UserMerchantRepository userMerchantRepository;

    @Autowired
    private UserMerchantService userMerchantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMerchantMockMvc;

    private UserMerchant userMerchant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMerchantResource userMerchantResource = new UserMerchantResource(userMerchantService);
        this.restUserMerchantMockMvc = MockMvcBuilders.standaloneSetup(userMerchantResource)
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
    public static UserMerchant createEntity(EntityManager em) {
        UserMerchant userMerchant = new UserMerchant();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userMerchant.setUser(user);
        // Add required entity
        Merchant merchant = MerchantResourceIntTest.createEntity(em);
        em.persist(merchant);
        em.flush();
        userMerchant.setMerchant(merchant);
        return userMerchant;
    }

    @Before
    public void initTest() {
        userMerchant = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMerchant() throws Exception {
        int databaseSizeBeforeCreate = userMerchantRepository.findAll().size();

        // Create the UserMerchant
        restUserMerchantMockMvc.perform(post("/api/user-merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMerchant)))
            .andExpect(status().isCreated());

        // Validate the UserMerchant in the database
        List<UserMerchant> userMerchantList = userMerchantRepository.findAll();
        assertThat(userMerchantList).hasSize(databaseSizeBeforeCreate + 1);
        UserMerchant testUserMerchant = userMerchantList.get(userMerchantList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserMerchantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMerchantRepository.findAll().size();

        // Create the UserMerchant with an existing ID
        userMerchant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMerchantMockMvc.perform(post("/api/user-merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMerchant)))
            .andExpect(status().isBadRequest());

        // Validate the UserMerchant in the database
        List<UserMerchant> userMerchantList = userMerchantRepository.findAll();
        assertThat(userMerchantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserMerchants() throws Exception {
        // Initialize the database
        userMerchantRepository.saveAndFlush(userMerchant);

        // Get all the userMerchantList
        restUserMerchantMockMvc.perform(get("/api/user-merchants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMerchant.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserMerchant() throws Exception {
        // Initialize the database
        userMerchantRepository.saveAndFlush(userMerchant);

        // Get the userMerchant
        restUserMerchantMockMvc.perform(get("/api/user-merchants/{id}", userMerchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMerchant.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserMerchant() throws Exception {
        // Get the userMerchant
        restUserMerchantMockMvc.perform(get("/api/user-merchants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMerchant() throws Exception {
        // Initialize the database
        userMerchantService.save(userMerchant);

        int databaseSizeBeforeUpdate = userMerchantRepository.findAll().size();

        // Update the userMerchant
        UserMerchant updatedUserMerchant = userMerchantRepository.findOne(userMerchant.getId());
        // Disconnect from session so that the updates on updatedUserMerchant are not directly saved in db
        em.detach(updatedUserMerchant);

        restUserMerchantMockMvc.perform(put("/api/user-merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserMerchant)))
            .andExpect(status().isOk());

        // Validate the UserMerchant in the database
        List<UserMerchant> userMerchantList = userMerchantRepository.findAll();
        assertThat(userMerchantList).hasSize(databaseSizeBeforeUpdate);
        UserMerchant testUserMerchant = userMerchantList.get(userMerchantList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMerchant() throws Exception {
        int databaseSizeBeforeUpdate = userMerchantRepository.findAll().size();

        // Create the UserMerchant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMerchantMockMvc.perform(put("/api/user-merchants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMerchant)))
            .andExpect(status().isCreated());

        // Validate the UserMerchant in the database
        List<UserMerchant> userMerchantList = userMerchantRepository.findAll();
        assertThat(userMerchantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserMerchant() throws Exception {
        // Initialize the database
        userMerchantService.save(userMerchant);

        int databaseSizeBeforeDelete = userMerchantRepository.findAll().size();

        // Get the userMerchant
        restUserMerchantMockMvc.perform(delete("/api/user-merchants/{id}", userMerchant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserMerchant> userMerchantList = userMerchantRepository.findAll();
        assertThat(userMerchantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMerchant.class);
        UserMerchant userMerchant1 = new UserMerchant();
        userMerchant1.setId(1L);
        UserMerchant userMerchant2 = new UserMerchant();
        userMerchant2.setId(userMerchant1.getId());
        assertThat(userMerchant1).isEqualTo(userMerchant2);
        userMerchant2.setId(2L);
        assertThat(userMerchant1).isNotEqualTo(userMerchant2);
        userMerchant1.setId(null);
        assertThat(userMerchant1).isNotEqualTo(userMerchant2);
    }
}
