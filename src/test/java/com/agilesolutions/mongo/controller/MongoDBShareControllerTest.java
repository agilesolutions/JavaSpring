// src/test/java/com/agilesolutions/mongo/controller/MongoDBShareControllerMockMvcTest.java
package com.agilesolutions.mongo.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.mongo.service.MongoDBShareService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MongoDBShareController.class)
class MongoDBShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MongoDBShareService shareService;

    @Test
    @DisplayName("Returns list of shares as JSON")
    void getAllSharesReturnsListOfShares() throws Exception {
        List<ShareDTO> shares = List.of(ShareDTO.builder().company("Share1").build(), ShareDTO.builder().company("Share2").build());
        when(shareService.getAllShares()).thenReturn(shares);

        mockMvc.perform(get("/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Share1"))
                .andExpect(jsonPath("$[1].name").value("Share2"));
    }

    @Test
    @DisplayName("Returns empty list when no shares exist")
    void getAllSharesReturnsEmptyList() throws Exception {
        when(shareService.getAllShares()).thenReturn(List.of());

        mockMvc.perform(get("/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Handles service exception with 500 error")
    void getAllSharesServiceThrowsException() throws Exception {
        when(shareService.getAllShares()).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/mongo/shares")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}