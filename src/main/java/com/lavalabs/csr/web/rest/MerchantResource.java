package com.lavalabs.csr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lavalabs.csr.config.Constants;
import com.lavalabs.csr.domain.Merchant;
import com.lavalabs.csr.domain.User;
import com.lavalabs.csr.repository.UserRepository;
import com.lavalabs.csr.service.MailService;
import com.lavalabs.csr.service.MerchantService;
import com.lavalabs.csr.service.UserService;
import com.lavalabs.csr.web.rest.errors.BadRequestAlertException;
import com.lavalabs.csr.web.rest.errors.EmailAlreadyUsedException;
import com.lavalabs.csr.web.rest.errors.InvalidPasswordException;
import com.lavalabs.csr.web.rest.errors.LoginAlreadyUsedException;
import com.lavalabs.csr.web.rest.util.HeaderUtil;
import com.lavalabs.csr.web.rest.vm.ManagedUserVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Merchant.
 */
@RestController
@RequestMapping("/api")
public class MerchantResource {

    private final Logger log = LoggerFactory.getLogger(MerchantResource.class);

    private static final String ENTITY_NAME = "merchant";

    private final MerchantService merchantService;

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;



    public MerchantResource(MerchantService merchantService, UserRepository userRepository, UserService userService, MailService mailService) {
        this.merchantService = merchantService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /merchants : Create a new merchant.
     *
     * @param merchant the merchant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchant, or with status 400 (Bad Request) if the merchant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchants")
    @Timed
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) throws URISyntaxException {
        log.debug("REST request to save Merchant : {}", merchant);
        if (merchant.getId() != null) {
            throw new BadRequestAlertException("A new merchant cannot already have an ID", ENTITY_NAME, "idexists");
        }

        //Create a user
        if (!AccountResource.checkPasswordLength(merchant.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(merchant.getLogin().toLowerCase()).ifPresent(u -> {throw new LoginAlreadyUsedException();});
        userRepository.findOneByEmailIgnoreCase(merchant.getEmail()).ifPresent(u -> {throw new EmailAlreadyUsedException();});

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin(merchant.getLogin());
        managedUserVM.setPassword(merchant.getPassword());
        managedUserVM.setEmail(merchant.getEmail());
        managedUserVM.setFirstName(merchant.getFirstName());
        managedUserVM.setLastName(merchant.getLastName());
        managedUserVM.setLangKey(Constants.DEFAULT_LANGUAGE); // default language

        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        log.debug("User registered : {}", user);
        mailService.sendActivationEmail(user);

        Merchant result = merchantService.save(merchant);
        return ResponseEntity.created(new URI("/api/merchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /merchants : Updates an existing merchant.
     *
     * @param merchant the merchant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchant,
     * or with status 400 (Bad Request) if the merchant is not valid,
     * or with status 500 (Internal Server Error) if the merchant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchants")
    @Timed
    public ResponseEntity<Merchant> updateMerchant(@RequestBody Merchant merchant) throws URISyntaxException {
        log.debug("REST request to update Merchant : {}", merchant);
        if (merchant.getId() == null) {
            return createMerchant(merchant);
        }
        Merchant result = merchantService.save(merchant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, merchant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchants : get all the merchants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of merchants in body
     */
    @GetMapping("/merchants")
    @Timed
    public List<Merchant> getAllMerchants() {
        log.debug("REST request to get all Merchants");
        return merchantService.findAll();
        }

    /**
     * GET  /merchants/:id : get the "id" merchant.
     *
     * @param id the id of the merchant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchant, or with status 404 (Not Found)
     */
    @GetMapping("/merchants/{id}")
    @Timed
    public ResponseEntity<Merchant> getMerchant(@PathVariable Long id) {
        log.debug("REST request to get Merchant : {}", id);
        Merchant merchant = merchantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(merchant));
    }

    /**
     * DELETE  /merchants/:id : delete the "id" merchant.
     *
     * @param id the id of the merchant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchants/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
        log.debug("REST request to delete Merchant : {}", id);
        merchantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
