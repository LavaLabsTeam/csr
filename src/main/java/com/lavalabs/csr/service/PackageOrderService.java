package com.lavalabs.csr.service;

import com.lavalabs.csr.domain.PackageOrder;
import com.lavalabs.csr.repository.PackageOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PackageOrder.
 */
@Service
@Transactional
public class PackageOrderService {

    private final Logger log = LoggerFactory.getLogger(PackageOrderService.class);

    private final PackageOrderRepository packageOrderRepository;

    public PackageOrderService(PackageOrderRepository packageOrderRepository) {
        this.packageOrderRepository = packageOrderRepository;
    }

    /**
     * Save a packageOrder.
     *
     * @param packageOrder the entity to save
     * @return the persisted entity
     */
    public PackageOrder save(PackageOrder packageOrder) {
        log.debug("Request to save PackageOrder : {}", packageOrder);
        return packageOrderRepository.save(packageOrder);
    }

    /**
     * Get all the packageOrders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PackageOrder> findAll() {
        log.debug("Request to get all PackageOrders");
        return packageOrderRepository.findAll();
    }

    /**
     * Get one packageOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PackageOrder findOne(Long id) {
        log.debug("Request to get PackageOrder : {}", id);
        return packageOrderRepository.findOne(id);
    }

    /**
     * Delete the packageOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PackageOrder : {}", id);
        packageOrderRepository.delete(id);
    }
}
