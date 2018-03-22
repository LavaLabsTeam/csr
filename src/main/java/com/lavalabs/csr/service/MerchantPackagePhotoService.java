package com.lavalabs.csr.service;

import com.lavalabs.csr.domain.MerchantPackagePhoto;
import com.lavalabs.csr.repository.MerchantPackagePhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing MerchantPackagePhoto.
 */
@Service
@Transactional
public class MerchantPackagePhotoService {

    private final Logger log = LoggerFactory.getLogger(MerchantPackagePhotoService.class);

    private final MerchantPackagePhotoRepository merchantPackagePhotoRepository;

    public MerchantPackagePhotoService(MerchantPackagePhotoRepository merchantPackagePhotoRepository) {
        this.merchantPackagePhotoRepository = merchantPackagePhotoRepository;
    }

    /**
     * Save a merchantPackagePhoto.
     *
     * @param merchantPackagePhoto the entity to save
     * @return the persisted entity
     */
    public MerchantPackagePhoto save(MerchantPackagePhoto merchantPackagePhoto) {
        log.debug("Request to save MerchantPackagePhoto : {}", merchantPackagePhoto);
        return merchantPackagePhotoRepository.save(merchantPackagePhoto);
    }

    /**
     * Get all the merchantPackagePhotos.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MerchantPackagePhoto> findAll() {
        log.debug("Request to get all MerchantPackagePhotos");
        return merchantPackagePhotoRepository.findAll();
    }

    /**
     * Get one merchantPackagePhoto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MerchantPackagePhoto findOne(Long id) {
        log.debug("Request to get MerchantPackagePhoto : {}", id);
        return merchantPackagePhotoRepository.findOne(id);
    }

    /**
     * Delete the merchantPackagePhoto by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MerchantPackagePhoto : {}", id);
        merchantPackagePhotoRepository.delete(id);
    }

    public List<MerchantPackagePhoto> findAllByMerchantPackageId(Long merchantPackageId) {
        return merchantPackagePhotoRepository.findAllByMerchantPackageId(merchantPackageId);
    }
}
