package com.lavalabs.csr.service;

import com.lavalabs.csr.domain.UserMerchant;
import com.lavalabs.csr.repository.UserMerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing UserMerchant.
 */
@Service
@Transactional
public class UserMerchantService {

    private final Logger log = LoggerFactory.getLogger(UserMerchantService.class);

    private final UserMerchantRepository userMerchantRepository;

    public UserMerchantService(UserMerchantRepository userMerchantRepository) {
        this.userMerchantRepository = userMerchantRepository;
    }

    /**
     * Save a userMerchant.
     *
     * @param userMerchant the entity to save
     * @return the persisted entity
     */
    public UserMerchant save(UserMerchant userMerchant) {
        log.debug("Request to save UserMerchant : {}", userMerchant);
        return userMerchantRepository.save(userMerchant);
    }

    /**
     * Get all the userMerchants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserMerchant> findAll() {
        log.debug("Request to get all UserMerchants");
        return userMerchantRepository.findAll();
    }

    /**
     * Get one userMerchant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserMerchant findOne(Long id) {
        log.debug("Request to get UserMerchant : {}", id);
        return userMerchantRepository.findOne(id);
    }

    /**
     * Delete the userMerchant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserMerchant : {}", id);
        userMerchantRepository.delete(id);
    }
}
