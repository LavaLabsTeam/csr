package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.Merchant;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Merchant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query("from Merchant m where lower(m.name) like %:name% and lower(m.location) like %:location%")
    List<Merchant> searchMerchant(@Param("name") String name,@Param("location") String location);
}
