package com.lavalabs.csr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.domain.MerchantPackage;
import com.lavalabs.csr.service.MerchantPackageService;
import com.lavalabs.csr.web.rest.errors.BadRequestAlertException;
import com.lavalabs.csr.web.rest.util.HeaderUtil;
import com.lavalabs.csr.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MerchantPackage.
 */
@RestController
@RequestMapping("/api")
public class MerchantPackageResource {

    private final Logger log = LoggerFactory.getLogger(MerchantPackageResource.class);

    private static final String ENTITY_NAME = "merchantPackage";

    private final MerchantPackageService merchantPackageService;

    public MerchantPackageResource(MerchantPackageService merchantPackageService) {
        this.merchantPackageService = merchantPackageService;
    }

    /**
     * POST  /merchant-packages : Create a new merchantPackage.
     *
     * @param merchantPackage the merchantPackage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchantPackage, or with status 400 (Bad Request) if the merchantPackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchant-packages")
    @Timed
    public ResponseEntity<MerchantPackage> createMerchantPackage(@RequestBody MerchantPackage merchantPackage) throws URISyntaxException {
        log.debug("REST request to save MerchantPackage : {}", merchantPackage);
        if (merchantPackage.getId() != null) {
            throw new BadRequestAlertException("A new merchantPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantPackage result = merchantPackageService.save(merchantPackage);
        return ResponseEntity.created(new URI("/api/merchant-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merchant-packages : Updates an existing merchantPackage.
     *
     * @param merchantPackage the merchantPackage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchantPackage,
     * or with status 400 (Bad Request) if the merchantPackage is not valid,
     * or with status 500 (Internal Server Error) if the merchantPackage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchant-packages")
    @Timed
    public ResponseEntity<MerchantPackage> updateMerchantPackage(@RequestBody MerchantPackage merchantPackage) throws URISyntaxException {
        log.debug("REST request to update MerchantPackage : {}", merchantPackage);
        if (merchantPackage.getId() == null) {
            return createMerchantPackage(merchantPackage);
        }
        MerchantPackage result = merchantPackageService.save(merchantPackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merchantPackage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchant-packages : get all the merchantPackages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of merchantPackages in body
     */
    @GetMapping("/merchant-packages")
    @Timed
    public ResponseEntity<List<MerchantPackage>> getAllMerchantPackages(Pageable pageable) {
        log.debug("REST request to get a page of MerchantPackages");
        Page<MerchantPackage> page = merchantPackageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/merchant-packages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /merchant-packages/:id : get the "id" merchantPackage.
     *
     * @param id the id of the merchantPackage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchantPackage, or with status 404 (Not Found)
     */
    @GetMapping("/merchant-packages/{id}")
    @Timed
    public ResponseEntity<MerchantPackage> getMerchantPackage(@PathVariable Long id) {
        log.debug("REST request to get MerchantPackage : {}", id);
        MerchantPackage merchantPackage = merchantPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(merchantPackage));
    }

    /**
     * DELETE  /merchant-packages/:id : delete the "id" merchantPackage.
     *
     * @param id the id of the merchantPackage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchant-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerchantPackage(@PathVariable Long id) {
        log.debug("REST request to delete MerchantPackage : {}", id);
        merchantPackageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
