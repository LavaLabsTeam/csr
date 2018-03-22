package com.lavalabs.csr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.domain.PackageOrder;
import com.lavalabs.csr.service.PackageOrderService;
import com.lavalabs.csr.web.rest.errors.BadRequestAlertException;
import com.lavalabs.csr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PackageOrder.
 */
@RestController
@RequestMapping("/api")
public class PackageOrderResource {

    private final Logger log = LoggerFactory.getLogger(PackageOrderResource.class);

    private static final String ENTITY_NAME = "packageOrder";

    private final PackageOrderService packageOrderService;

    public PackageOrderResource(PackageOrderService packageOrderService) {
        this.packageOrderService = packageOrderService;
    }

    /**
     * POST  /package-orders : Create a new packageOrder.
     *
     * @param packageOrder the packageOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new packageOrder, or with status 400 (Bad Request) if the packageOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/package-orders")
    @Timed
    public ResponseEntity<PackageOrder> createPackageOrder(@Valid @RequestBody PackageOrder packageOrder) throws URISyntaxException {
        log.debug("REST request to save PackageOrder : {}", packageOrder);
        if (packageOrder.getId() != null) {
            throw new BadRequestAlertException("A new packageOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackageOrder result = packageOrderService.save(packageOrder);
        return ResponseEntity.created(new URI("/api/package-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-orders : Updates an existing packageOrder.
     *
     * @param packageOrder the packageOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated packageOrder,
     * or with status 400 (Bad Request) if the packageOrder is not valid,
     * or with status 500 (Internal Server Error) if the packageOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/package-orders")
    @Timed
    public ResponseEntity<PackageOrder> updatePackageOrder(@Valid @RequestBody PackageOrder packageOrder) throws URISyntaxException {
        log.debug("REST request to update PackageOrder : {}", packageOrder);
        if (packageOrder.getId() == null) {
            return createPackageOrder(packageOrder);
        }
        PackageOrder result = packageOrderService.save(packageOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, packageOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-orders : get all the packageOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of packageOrders in body
     */
    @GetMapping("/package-orders")
    @Timed
    public List<PackageOrder> getAllPackageOrders() {
        log.debug("REST request to get all PackageOrders");
        return packageOrderService.findAll();
        }

    /**
     * GET  /package-orders/:id : get the "id" packageOrder.
     *
     * @param id the id of the packageOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the packageOrder, or with status 404 (Not Found)
     */
    @GetMapping("/package-orders/{id}")
    @Timed
    public ResponseEntity<PackageOrder> getPackageOrder(@PathVariable Long id) {
        log.debug("REST request to get PackageOrder : {}", id);
        PackageOrder packageOrder = packageOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(packageOrder));
    }

    /**
     * DELETE  /package-orders/:id : delete the "id" packageOrder.
     *
     * @param id the id of the packageOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/package-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePackageOrder(@PathVariable Long id) {
        log.debug("REST request to delete PackageOrder : {}", id);
        packageOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
