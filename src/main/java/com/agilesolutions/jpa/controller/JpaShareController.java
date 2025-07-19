package com.agilesolutions.jpa.controller;

import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.service.JpaShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/jpa/shares")
public class JpaShareController {

    private final JpaShareService shareService;

    @GetMapping
    Iterable<Share> getAllShares() {

        log.info("Get all shares");
        return shareService.getAllShares();
    }


}