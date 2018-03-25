package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.MerchantPackagePhoto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MerchantPackagePhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantPackagePhotoRepository extends JpaRepository<MerchantPackagePhoto, Long> {

    List<MerchantPackagePhoto> findAllByMerchantPackageId(Long merchantPackageId);

    @Query("from MerchantPackagePhoto mpp where mpp.merchantPackage.merchant.id = :merchantId")
    List<MerchantPackagePhoto> findAllByMerchantId(@Param("merchantId") Long merchantId);

}
