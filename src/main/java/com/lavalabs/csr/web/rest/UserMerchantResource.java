package com.lavalabs.csr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.domain.UserMerchant;
import com.lavalabs.csr.service.UserMerchantService;
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
 * REST controller for managing UserMerchant.
 */
@RestController
@RequestMapping("/api")
public class UserMerchantResource {

    private final Logger log = LoggerFactory.getLogger(UserMerchantResource.class);

    private static final String ENTITY_NAME = "userMerchant";

    private final UserMerchantService userMerchantService;

    public UserMerchantResource(UserMerchantService userMerchantService) {
        this.userMerchantService = userMerchantService;
    }

    /**
     * POST  /user-merchants : Create a new userMerchant.
     *
     * @param userMerchant the userMerchant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMerchant, or with status 400 (Bad Request) if the userMerchant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-merchants")
    @Timed
    public ResponseEntity<UserMerchant> createUserMerchant(@Valid @RequestBody UserMerchant userMerchant) throws URISyntaxException {
        log.debug("REST request to save UserMerchant : {}", userMerchant);
        if (userMerchant.getId() != null) {
            throw new BadRequestAlertException("A new userMerchant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMerchant result = userMerchantService.save(userMerchant);
        return ResponseEntity.created(new URI("/api/user-merchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-merchants : Updates an existing userMerchant.
     *
     * @param userMerchant the userMerchant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMerchant,
     * or with status 400 (Bad Request) if the userMerchant is not valid,
     * or with status 500 (Internal Server Error) if the userMerchant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-merchants")
    @Timed
    public ResponseEntity<UserMerchant> updateUserMerchant(@Valid @RequestBody UserMerchant userMerchant) throws URISyntaxException {
        log.debug("REST request to update UserMerchant : {}", userMerchant);
        if (userMerchant.getId() == null) {
            return createUserMerchant(userMerchant);
        }
        UserMerchant result = userMerchantService.save(userMerchant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMerchant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-merchants : get all the userMerchants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userMerchants in body
     */
    @GetMapping("/user-merchants")
    @Timed
    public List<UserMerchant> getAllUserMerchants() {
        log.debug("REST request to get all UserMerchants");
        return userMerchantService.findAll();
        }

    /**
     * GET  /user-merchants/:id : get the "id" userMerchant.
     *
     * @param id the id of the userMerchant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMerchant, or with status 404 (Not Found)
     */
    @GetMapping("/user-merchants/{id}")
    @Timed
    public ResponseEntity<UserMerchant> getUserMerchant(@PathVariable Long id) {
        log.debug("REST request to get UserMerchant : {}", id);
        UserMerchant userMerchant = userMerchantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userMerchant));
    }

    /**
     * DELETE  /user-merchants/:id : delete the "id" userMerchant.
     *
     * @param id the id of the userMerchant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-merchants/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserMerchant(@PathVariable Long id) {
        log.debug("REST request to delete UserMerchant : {}", id);
        userMerchantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
