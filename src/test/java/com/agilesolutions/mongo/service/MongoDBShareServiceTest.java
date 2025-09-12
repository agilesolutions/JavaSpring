package com.agilesolutions.mongo.service;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.mongo.model.Share;
import com.agilesolutions.mongo.repository.MongoDBShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the MongoDBShareService class.
 * This class tests the functionality of retrieving shares from the MongoDBShareRepository
 * and converting them into ShareDTO objects.
 */
class MongoDBShareServiceTest {

    @Mock
    private MongoDBShareRepository shareRepository; // Mocked repository for accessing share data.

    @InjectMocks
    private MongoDBShareService mongoDBShareService; // Service under test.

    /**
     * Initializes mocks before each test execution.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that getAllShares returns a list of ShareDTOs when shares exist in the repository.
     */
    @Test
    @DisplayName("getAllShares returns list of ShareDTOs when shares exist")
    void getAllSharesReturnsListOfShareDTOsWhenSharesExist() {
        // Arrange: Create a sample Share object.
        Share share = Share.builder().company("Company A").quantity(100).build();

        // Mock the repository to return the sample Share object.
        when(shareRepository.findAll()).thenReturn(List.of(share));

        // Act: Call the service method to retrieve all shares.
        List<ShareDTO> result = mongoDBShareService.getAllShares();

        // Assert: Verify the returned list contains the expected ShareDTO.
        assertEquals(1, result.size());
        assertEquals("Company A", result.get(0).company());
        assertEquals(100, result.get(0).quantity());
    }

    /**
     * Tests that getAllShares returns an empty list when no shares exist in the repository.
     */
    @Test
    @DisplayName("getAllShares returns empty list when no shares exist")
    void getAllSharesReturnsEmptyListWhenNoSharesExist() {
        // Mock the repository to return an empty list.
        when(shareRepository.findAll()).thenReturn(Collections.emptyList());

        // Act: Call the service method to retrieve all shares.
        List<ShareDTO> result = mongoDBShareService.getAllShares();

        // Assert: Verify the returned list is empty.
        assertEquals(0, result.size());
    }
}