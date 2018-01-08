package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.UserMerchant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserMerchant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMerchantRepository extends JpaRepository<UserMerchant, Long> {

    @Query("select user_merchant from UserMerchant user_merchant where user_merchant.user.login = ?#{principal.username}")
    List<UserMerchant> findByUserIsCurrentUser();

}
