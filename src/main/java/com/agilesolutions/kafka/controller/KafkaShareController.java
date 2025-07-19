package com.agilesolutions.kafka.controller;

import com.agilesolutions.kafka.model.Share;
import com.agilesolutions.kafka.service.KafkaShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/kafka/shares")
public class KafkaShareController {

    private final KafkaShareService shareService;

    @GetMapping(produces = "application/avro+json")
    List<Share> getAllShares() {

        log.info("Get all shares");
        return shareService.getAllShares();
    }


}