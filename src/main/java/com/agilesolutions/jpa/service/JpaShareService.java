package com.agilesolutions.jpa.service;

import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.repository.JpaShareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Share entities.
 */
@Service
public class JpaShareService {

    // Inject your repository here (e.g., JpaShareRepository)
    private final JpaShareRepository shareRepository;

    public JpaShareService(JpaShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    /**
     * Retrieves all shares.
     *
     * @return a list of all Share entities
     */
    public List<Share> getAllShares() {
        return (List<Share>) shareRepository.findAll();
    }

    /**
     * Retrieves a share by its ID.
     *
     * @param id the ID of the share
     * @return an Optional containing the Share if found
     */
    public Optional<Share> getShareById(Long id) {
        return shareRepository.findById(id);
    }

    /**
     * Creates a new share.
     *
     * @param share the Share entity to create
     * @return the created Share entity
     */
    public Share createShare(Share share) {
        return shareRepository.save(share);
    }

    /**
     * Updates an existing share.
     *
     * @param id the ID of the share to update
     * @param share the updated Share entity
     * @return an Optional containing the updated Share if found
     */
    public Optional<Share> updateShare(Long id, Share share) {
        return shareRepository.findById(id)
                .map(existingShare -> {
                    existingShare.setCompany(share.getCompany());
                    existingShare.setQuantity(share.getQuantity());
                    return shareRepository.save(existingShare);
                });
    }

    /**
     * Deletes a share by its ID.
     *
     * @param id the ID of the share to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteShare(Long id) {
        if (shareRepository.existsById(id)) {
            shareRepository.deleteById(id);
            return true;
        }
        return false;
    }
}