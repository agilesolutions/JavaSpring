package com.agilesolutions.mongo.controller;

import com.agilesolutions.mongo.model.Share;
import com.agilesolutions.mongo.service.MongoDBShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mongo/shares")
public class MongoDBShareController {

    private final MongoDBShareService shareService;

    @GetMapping
    Iterable<Share> getAllShares() {

        log.info("Get all shares");
        return shareService.getAllShares();
    }


}