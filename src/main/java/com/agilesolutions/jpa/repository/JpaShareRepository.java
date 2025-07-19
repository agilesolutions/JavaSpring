package com.agilesolutions.jpa.repository;

import com.agilesolutions.jpa.model.Share;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface JpaShareRepository extends CrudRepository<Share, Long> {


    // it works if it matches the share field name
    List<Share> findByCompany(String company);

    // Custom Query
    @Query("SELECT s FROM Share s WHERE s.updated > :date")
    List<Share> findByPublishedDateAfter(@Param("date") LocalDate date);

}