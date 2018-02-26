package com.lavalabs.csr.service;

import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.repository.MerchantPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing MerchantPackage.
 */
@Service
@Transactional
public class MerchantPackageService {

    private final Logger log = LoggerFactory.getLogger(MerchantPackageService.class);

    private final MerchantPackageRepository merchantPackageRepository;

    public MerchantPackageService(MerchantPackageRepository merchantPackageRepository) {
        this.merchantPackageRepository = merchantPackageRepository;
    }

    /**
     * Save a merchantPackage.
     *
     * @param merchantPackage the entity to save
     * @return the persisted entity
     */
    public MerchantPackage save(MerchantPackage merchantPackage) {
        log.debug("Request to save MerchantPackage : {}", merchantPackage);
        return merchantPackageRepository.save(merchantPackage);
    }

    /**
     * Get all the merchantPackages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MerchantPackage> findAll(Pageable pageable) {
        log.debug("Request to get all MerchantPackages");
        return merchantPackageRepository.findAll(pageable);
    }

    /**
     * Get one merchantPackage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MerchantPackage findOne(Long id) {
        log.debug("Request to get MerchantPackage : {}", id);
        return merchantPackageRepository.findOne(id);
    }

    /**
     * Delete the merchantPackage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MerchantPackage : {}", id);
        merchantPackageRepository.delete(id);
    }

    public List<MerchantPackage> findAllByCategoryId(Long categoryId) {
        return merchantPackageRepository.findAllByCategoryId(categoryId);
    }
}
