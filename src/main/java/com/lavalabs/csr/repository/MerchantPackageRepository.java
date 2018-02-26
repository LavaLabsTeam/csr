package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.MerchantPackage;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MerchantPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantPackageRepository extends JpaRepository<MerchantPackage, Long> {

    @Query("from MerchantPackage mp where lower(mp.name) like %:query%")
    List<MerchantPackage> searchMerchantPackage(@Param("query") String query);

    List<MerchantPackage> findAllByCategoryId(Long categoryId);
}
