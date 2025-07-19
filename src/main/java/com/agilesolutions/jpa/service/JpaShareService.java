package com.agilesolutions.jpa.service;

import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.repository.JpaShareRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JpaShareService {

    @Autowired
    private final JpaShareRepository shareRepository;

    public Iterable<Share> getAllShares() {

        log.info("Get all Shares");

        return shareRepository.findByCompany("AAPL");

    }

}
