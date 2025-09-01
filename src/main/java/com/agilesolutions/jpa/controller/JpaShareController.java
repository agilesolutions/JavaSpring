package com.agilesolutions.jpa.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.exception.BusinessException;
import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.service.JpaShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling JPA share-related HTTP requests.
 * Provides endpoints to interact with Share entities using JPA.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/jpa/shares")
public class JpaShareController {

    private final JpaShareService shareService;

    /**
     * Retrieves all shares from the JpaShareService.
     *
     * @return an iterable collection of Share objects.
     */
    @GetMapping(produces = "application/json")
    public Iterable<ShareDTO> getAllShares() {
        log.info("Get all shares");
        return shareService.getAllShares();
    }

    /**
     * Retrieves a share by its ID.
     *
     * @param id the ID of the share
     * @return the Share object if found, or a 404 response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Share> getShareById(@PathVariable Long id) {
        log.info("Get share by ID: {}", id);
        return shareService.getShareById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("Share with ID " + id + " not found"));
    }

    /**
     * Creates a new share.
     *
     * @param share the Share object to create
     * @return the created Share object
     */
    @PostMapping
    public ResponseEntity<Share> createShare(@RequestBody Share share) {
        log.info("Create new share: {}", share);
        Share createdShare = shareService.createShare(share);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShare);
    }

    /**
     * Updates an existing share.
     *
     * @param id    the ID of the share to update
     * @param share the updated Share object
     * @return the updated Share object, or a 404 response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Share> updateShare(@PathVariable Long id, @RequestBody Share share) {
        log.info("Update share with ID: {}", id);
        return shareService.updateShare(id, share)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BusinessException("Share with ID " + id + " not found"));
    }

    /**
     * Deletes a share by its ID.
     *
     * @param id the ID of the share to delete
     * @return a 204 response if successful, or a 404 response if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        log.info("Delete share with ID: {}", id);
        if (shareService.deleteShare(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new BusinessException("Share with ID " + id + " not found");
        }
    }
}