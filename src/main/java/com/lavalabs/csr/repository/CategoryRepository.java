package com.lavalabs.csr.repository;

import com.lavalabs.csr.domain.Category;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("from Category c where lower(c.title) like %:query%")
    List<Category> searchCategory(@Param("query") String query);
}


