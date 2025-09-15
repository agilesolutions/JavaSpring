package com.agilesolutions.mongo.service;

import com.agilesolutions.dto.ShareDto;
import com.agilesolutions.mongo.model.Share;
import com.agilesolutions.mongo.repository.MongoDBShareRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Share entities.
 */
@Service
@Slf4j
@AllArgsConstructor
public class MongoDBShareService {

    @Autowired
    private final MongoDBShareRepository shareRepository;

    /**
     * Retrieves all shares and maps them to ShareDTOs.
     *
     * @return a list of ShareDTO objects
     */
    public List<ShareDto> getAllShares() {
        return ((List<Share>) shareRepository.findAll()).stream()
                .map(s -> ShareDto.builder().company(s.getCompany()).quantity(s.getQuantity()).build())
                .collect(Collectors.toList());
    }

}
