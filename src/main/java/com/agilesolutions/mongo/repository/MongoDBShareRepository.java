package com.agilesolutions.mongo.repository;

import com.agilesolutions.mongo.model.Share;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MongoDBShareRepository extends CrudRepository<Share, Long> {

    @Query("{company:'?0'}")
    Share findItemByCompany(String company);

}