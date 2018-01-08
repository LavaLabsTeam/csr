package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.MerchantPackage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MerchantPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantPackageRepository extends JpaRepository<MerchantPackage, Long> {

}
