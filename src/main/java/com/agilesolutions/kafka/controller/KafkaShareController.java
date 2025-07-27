package com.agilesolutions.kafka.controller;

import com.agilesolutions.dto.Share;
import com.agilesolutions.kafka.service.KafkaShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling Kafka share-related HTTP requests.
 * Demonstrates the use of AVRO HTTP Message converter.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/kafka/shares")
public class KafkaShareController {

    private final KafkaShareService shareService;

    /**
     * Retrieves all shares from the KafkaShareService.
     *
     * @return a list of Share objects in JSON format.
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    List<Share> getAllShares() {
        log.info("Get all shares");
        return shareService.getAllShares()
                .stream()
                .map(s -> Share.builder()
                        .id(s.getId())
                        .company(s.getCompany())
                        .Quantity(s.getQuantity())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }
}