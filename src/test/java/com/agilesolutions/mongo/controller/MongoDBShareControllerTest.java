package com.agilesolutions.mongo.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.exception.CustomControllerAdvice;
import com.agilesolutions.mongo.service.MongoDBShareService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class contains unit tests for the MongoDBShareController using MockMvc.
 * It verifies the behavior of the controller's HTTP endpoints by mocking the service layer.
 */
@WebMvcTest(MongoDBShareController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {MongoDBShareController.class, CustomControllerAdvice.class})
class MongoDBShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MongoDBShareService shareService;

    /**
     * Tests that the endpoint returns a list of shares as JSON when shares exist.
     * Verifies the HTTP status is 200 (OK) and the response contains the expected data.
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("Returns list of shares as JSON")
    void getAllSharesReturnsListOfShares() throws Exception {
        List<ShareDTO> shares = List.of(
                new ShareDTO("Share1", 1),
                new ShareDTO("Share2", 2)
        );
        when(shareService.getAllShares()).thenReturn(shares);

        mockMvc.perform(get("/api/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].company").value("Share1"))
                .andExpect(jsonPath("$[1].company").value("Share2"));
    }

    /**
     * Tests that the endpoint returns an empty list as JSON when no shares exist.
     * Verifies the HTTP status is 200 (OK) and the response is an empty array.
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("Returns empty list when no shares exist")
    void getAllSharesReturnsEmptyList() throws Exception {
        when(shareService.getAllShares()).thenReturn(List.of());

        mockMvc.perform(get("/api/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    /**
     * Tests that the endpoint handles a service exception by returning a 500 (Internal Server Error) status.
     * Verifies the HTTP status is 500 when the service layer throws an exception.
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("Handles service exception with 500 error")
    void getAllSharesServiceThrowsException() throws Exception {
        when(shareService.getAllShares()).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/api/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}