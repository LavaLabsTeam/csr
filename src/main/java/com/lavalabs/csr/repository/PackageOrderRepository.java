package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.PackageOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PackageOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder, Long> {

}
