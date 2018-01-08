package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.Merchant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Merchant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
