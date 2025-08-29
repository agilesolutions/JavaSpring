package com.agilesolutions.mongo.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.mongo.service.MongoDBShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling MongoDB share-related HTTP requests.
 * Provides endpoints to interact with Share entities stored in MongoDB.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/mongo/shares")
public class MongoDBShareController {

    private final MongoDBShareService shareService;

    /**
     * Retrieves all shares from the MongoDBShareService.
     *
     * @return an iterable collection of Share objects.
     */
    @GetMapping
    public List<ShareDTO> getAllShares() {
        log.info("Get all shares");
        return shareService.getAllShares();
    }
}