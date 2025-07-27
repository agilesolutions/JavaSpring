package com.agilesolutions.jpa.service;

import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.repository.JpaShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the JpaShareService class.
 * This test class verifies the behavior of JpaShareService methods using mocked dependencies.
 */
class JpaShareServiceTest {

    @Mock
    private JpaShareRepository shareRepository; // Mocked repository for Share entities.

    @InjectMocks
    private JpaShareService shareService; // Service under test.

    /**
     * Initializes mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests for the getAllShares method in JpaShareService.
     */
    @Nested
    @DisplayName("getAllShares")
    class GetAllShares {

        /**
         * Verifies that all shares are returned when the repository is not empty.
         */
        @Test
        @DisplayName("returns all shares when repository is not empty")
        void returnsAllShares() {
            List<Share> shares = List.of(new Share(), new Share());
            when(shareRepository.findAll()).thenReturn(shares);

            List<Share> result = shareService.getAllShares();

            assertEquals(2, result.size());
            verify(shareRepository, times(1)).findAll();
        }

        /**
         * Verifies that an empty list is returned when the repository is empty.
         */
        @Test
        @DisplayName("returns empty list when repository is empty")
        void returnsEmptyList() {
            when(shareRepository.findAll()).thenReturn(List.of());

            List<Share> result = shareService.getAllShares();

            assertTrue(result.isEmpty());
            verify(shareRepository, times(1)).findAll();
        }
    }

    /**
     * Tests for the getShareById method in JpaShareService.
     */
    @Nested
    @DisplayName("getShareById")
    class GetShareById {

        /**
         * Verifies that a share is returned when the given ID exists in the repository.
         */
        @Test
        @DisplayName("returns share when ID exists")
        void returnsShareWhenIdExists() {
            Share share = new Share();
            when(shareRepository.findById(1L)).thenReturn(Optional.of(share));

            Optional<Share> result = shareService.getShareById(1L);

            assertTrue(result.isPresent());
            assertEquals(share, result.get());
            verify(shareRepository, times(1)).findById(1L);
        }

        /**
         * Verifies that an empty optional is returned when the given ID does not exist in the repository.
         */
        @Test
        @DisplayName("returns empty optional when ID does not exist")
        void returnsEmptyOptionalWhenIdDoesNotExist() {
            when(shareRepository.findById(1L)).thenReturn(Optional.empty());

            Optional<Share> result = shareService.getShareById(1L);

            assertTrue(result.isEmpty());
            verify(shareRepository, times(1)).findById(1L);
        }
    }

    /**
     * Tests for the createShare method in JpaShareService.
     */
    @Nested
    @DisplayName("createShare")
    class CreateShare {

        /**
         * Verifies that a share is saved and returned correctly.
         */
        @Test
        @DisplayName("saves and returns the created share")
        void savesAndReturnsCreatedShare() {
            Share share = new Share();
            when(shareRepository.save(share)).thenReturn(share);

            Share result = shareService.createShare(share);

            assertEquals(share, result);
            verify(shareRepository, times(1)).save(share);
        }
    }

    /**
     * Tests for the updateShare method in JpaShareService.
     */
    @Nested
    @DisplayName("updateShare")
    class UpdateShare {

        /**
         * Verifies that a share is updated and returned when the given ID exists.
         */
        @Test
        @DisplayName("updates and returns the share when ID exists")
        void updatesAndReturnsShareWhenIdExists() {
            Share existingShare = new Share();
            existingShare.setCompany("Old Company");
            existingShare.setQuantity(10);

            Share updatedShare = new Share();
            updatedShare.setCompany("New Company");
            updatedShare.setQuantity(20);

            when(shareRepository.findById(1L)).thenReturn(Optional.of(existingShare));
            when(shareRepository.save(existingShare)).thenReturn(existingShare);

            Optional<Share> result = shareService.updateShare(1L, updatedShare);

            assertTrue(result.isPresent());
            assertEquals("New Company", result.get().getCompany());
            assertEquals(20, result.get().getQuantity());
            verify(shareRepository, times(1)).findById(1L);
            verify(shareRepository, times(1)).save(existingShare);
        }

        /**
         * Verifies that an empty optional is returned when the given ID does not exist.
         */
        @Test
        @DisplayName("returns empty optional when ID does not exist")
        void returnsEmptyOptionalWhenIdDoesNotExist() {
            Share updatedShare = new Share();
            when(shareRepository.findById(1L)).thenReturn(Optional.empty());

            Optional<Share> result = shareService.updateShare(1L, updatedShare);

            assertTrue(result.isEmpty());
            verify(shareRepository, times(1)).findById(1L);
            verify(shareRepository, never()).save(any());
        }
    }

    /**
     * Tests for the deleteShare method in JpaShareService.
     */
    @Nested
    @DisplayName("deleteShare")
    class DeleteShare {

        /**
         * Verifies that a share is deleted and true is returned when the given ID exists.
         */
        @Test
        @DisplayName("deletes the share and returns true when ID exists")
        void deletesShareAndReturnsTrueWhenIdExists() {
            when(shareRepository.existsById(1L)).thenReturn(true);

            boolean result = shareService.deleteShare(1L);

            assertTrue(result);
            verify(shareRepository, times(1)).existsById(1L);
            verify(shareRepository, times(1)).deleteById(1L);
        }

        /**
         * Verifies that false is returned when the given ID does not exist.
         */
        @Test
        @DisplayName("returns false when ID does not exist")
        void returnsFalseWhenIdDoesNotExist() {
            when(shareRepository.existsById(1L)).thenReturn(false);

            boolean result = shareService.deleteShare(1L);

            assertFalse(result);
            verify(shareRepository, times(1)).existsById(1L);
            verify(shareRepository, never()).deleteById(anyLong());
        }
    }
}