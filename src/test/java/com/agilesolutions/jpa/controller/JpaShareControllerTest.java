package com.agilesolutions.jpa.controller;

import com.agilesolutions.exception.CustomControllerAdvice;
import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.service.JpaShareService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JpaShareController.class)
@ContextConfiguration(classes = {JpaShareController.class, CustomControllerAdvice.class})
class JpaShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JpaShareService shareService;

    @Test
    void givenShareExists_whenGetShareById_thenReturnShare() throws Exception {
        // GIVEN
        Share share = new Share(1L, "Company A", 100, LocalDate.now());
        when(shareService.getShareById(1L)).thenReturn(Optional.of(share));

        // WHEN & THEN
        mockMvc.perform(get("/jpa/shares/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"company\":\"Company A\",\"quantity\":100}"));
    }

    @Test
    void givenShareDoesNotExist_whenGetShareById_thenReturnNotFound() throws Exception {
        // GIVEN
        when(shareService.getShareById(1L)).thenReturn(Optional.empty());

        // WHEN & THEN
        mockMvc.perform(get("/jpa/shares/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateShare_thenReturnCreatedShare() throws Exception {
        // GIVEN
        Share share = new Share(1L, "Company A", 100, LocalDate.now());
        when(shareService.createShare(Mockito.any(Share.class))).thenReturn(share);

        // WHEN & THEN
        mockMvc.perform(post("/jpa/shares")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"company\":\"Company A\",\"quantity\":100}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"company\":\"Company A\",\"quantity\":100}"));
    }

    @Test
    void givenShareExists_whenUpdateShare_thenReturnUpdatedShare() throws Exception {
        // GIVEN
        Share updatedShare = new Share(1L, "Company B", 200, LocalDate.now());
        when(shareService.updateShare(Mockito.eq(1L), Mockito.any(Share.class))).thenReturn(Optional.of(updatedShare));

        // WHEN & THEN
        mockMvc.perform(put("/jpa/shares/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"company\":\"Company B\",\"quantity\":200}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"company\":\"Company B\",\"quantity\":200}"));
    }

    @Test
    void givenShareDoesNotExist_whenUpdateShare_thenReturnNotFound() throws Exception {
        // GIVEN
        when(shareService.updateShare(Mockito.eq(1L), Mockito.any(Share.class))).thenReturn(Optional.empty());

        // WHEN & THEN
        mockMvc.perform(put("/jpa/shares/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"company\":\"Company B\",\"quantity\":200}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenShareExists_whenDeleteShare_thenReturnNoContent() throws Exception {
        // GIVEN
        when(shareService.deleteShare(1L)).thenReturn(true);

        // WHEN & THEN
        mockMvc.perform(delete("/jpa/shares/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenShareDoesNotExist_whenDeleteShare_thenReturnNotFound() throws Exception {
        // GIVEN
        when(shareService.deleteShare(1L)).thenReturn(false);

        // WHEN & THEN
        mockMvc.perform(delete("/jpa/shares/1"))
                .andExpect(status().isBadRequest());
    }
}