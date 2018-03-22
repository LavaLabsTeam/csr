package com.lavalabs.csr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.domain.MerchantPackagePhoto;
import com.lavalabs.csr.service.MerchantPackagePhotoService;
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
 * REST controller for managing MerchantPackagePhoto.
 */
@RestController
@RequestMapping("/api")
public class MerchantPackagePhotoResource {

    private final Logger log = LoggerFactory.getLogger(MerchantPackagePhotoResource.class);

    private static final String ENTITY_NAME = "merchantPackagePhoto";

    private final MerchantPackagePhotoService merchantPackagePhotoService;

    public MerchantPackagePhotoResource(MerchantPackagePhotoService merchantPackagePhotoService) {
        this.merchantPackagePhotoService = merchantPackagePhotoService;
    }

    /**
     * POST  /merchant-package-photos : Create a new merchantPackagePhoto.
     *
     * @param merchantPackagePhoto the merchantPackagePhoto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchantPackagePhoto, or with status 400 (Bad Request) if the merchantPackagePhoto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchant-package-photos")
    @Timed
    public ResponseEntity<MerchantPackagePhoto> createMerchantPackagePhoto(@Valid @RequestBody MerchantPackagePhoto merchantPackagePhoto) throws URISyntaxException {
        log.debug("REST request to save MerchantPackagePhoto : {}", merchantPackagePhoto);
        if (merchantPackagePhoto.getId() != null) {
            throw new BadRequestAlertException("A new merchantPackagePhoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MerchantPackagePhoto result = merchantPackagePhotoService.save(merchantPackagePhoto);
        return ResponseEntity.created(new URI("/api/merchant-package-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merchant-package-photos : Updates an existing merchantPackagePhoto.
     *
     * @param merchantPackagePhoto the merchantPackagePhoto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchantPackagePhoto,
     * or with status 400 (Bad Request) if the merchantPackagePhoto is not valid,
     * or with status 500 (Internal Server Error) if the merchantPackagePhoto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchant-package-photos")
    @Timed
    public ResponseEntity<MerchantPackagePhoto> updateMerchantPackagePhoto(@Valid @RequestBody MerchantPackagePhoto merchantPackagePhoto) throws URISyntaxException {
        log.debug("REST request to update MerchantPackagePhoto : {}", merchantPackagePhoto);
        if (merchantPackagePhoto.getId() == null) {
            return createMerchantPackagePhoto(merchantPackagePhoto);
        }
        MerchantPackagePhoto result = merchantPackagePhotoService.save(merchantPackagePhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merchantPackagePhoto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchant-package-photos : get all the merchantPackagePhotos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of merchantPackagePhotos in body
     */
    @GetMapping("/merchant-package-photos")
    @Timed
    public List<MerchantPackagePhoto> getAllMerchantPackagePhotos() {
        log.debug("REST request to get all MerchantPackagePhotos");
        return merchantPackagePhotoService.findAll();
    }


    /**
     * GET  /merchant-package-photos : get all the merchantPackagePhotos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of merchantPackagePhotos in body
     */
    @GetMapping("/merchant-package-photos-by-merchant-package/:merchantPackageId")
    @Timed
    public List<MerchantPackagePhoto> getAllMerchantPackagePhotosByMerchantPackage(@PathVariable Long merchantPackageId) {
        log.debug("REST request to get all MerchantPackagePhotos");
        return merchantPackagePhotoService.findAllByMerchantPackageId(merchantPackageId);
    }


    /**
     * GET  /merchant-package-photos/:id : get the "id" merchantPackagePhoto.
     *
     * @param id the id of the merchantPackagePhoto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchantPackagePhoto, or with status 404 (Not Found)
     */
    @GetMapping("/merchant-package-photos/{id}")
    @Timed
    public ResponseEntity<MerchantPackagePhoto> getMerchantPackagePhoto(@PathVariable Long id) {
        log.debug("REST request to get MerchantPackagePhoto : {}", id);
        MerchantPackagePhoto merchantPackagePhoto = merchantPackagePhotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(merchantPackagePhoto));
    }

    /**
     * DELETE  /merchant-package-photos/:id : delete the "id" merchantPackagePhoto.
     *
     * @param id the id of the merchantPackagePhoto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchant-package-photos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerchantPackagePhoto(@PathVariable Long id) {
        log.debug("REST request to delete MerchantPackagePhoto : {}", id);
        merchantPackagePhotoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
