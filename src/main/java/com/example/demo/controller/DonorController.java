package com.example.demo.controller;

import com.example.demo.model.Donor;
import com.example.demo.model.Document;
import com.example.demo.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    // Create a new donor
    @PostMapping
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        Donor createdDonor = donorService.createDonor(donor);
        return ResponseEntity.ok(createdDonor);
    }

    // Get donor by ID
    @GetMapping("/by-user/{userId}")
public ResponseEntity<Donor> getDonorByUserId(@PathVariable Long userId) {
    Donor donor = donorService.getDonorByUserId(userId); // ✅ correct service method
    return ResponseEntity.ok(donor);
}



    // Update donor
    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @RequestBody Donor donorDetails) {
        Donor updatedDonor = donorService.updateDonor(id, donorDetails);
        return ResponseEntity.ok(updatedDonor);
    }

    // Get donor consent status
    @GetMapping("/{id}/consent")
    public ResponseEntity<String> getConsentStatus(@PathVariable Long id) {
        String status = donorService.getConsentStatus(id);
        return ResponseEntity.ok(status);
    }

    // Update donor consent status
    @PutMapping("/{id}/consent")
    public ResponseEntity<Donor> updateConsentStatus(@PathVariable Long id, @RequestParam String status) {
        Donor updatedDonor = donorService.updateConsentStatus(id, status);
        return ResponseEntity.ok(updatedDonor);
    }

    // Upload document for donor — UPDATED to accept JSON payload in request body
    @PostMapping("/{id}/documents")
    public ResponseEntity<Document> uploadDocument(
            @PathVariable Long id,
            @RequestBody Document documentPayload) {

        // documentPayload contains fileName, fileType, filePath (and possibly other fields)
        Document saved = donorService.uploadDocument(
                id,
                documentPayload.getFileName(),
                documentPayload.getFileType(),
                documentPayload.getFilePath()
        );
        return ResponseEntity.ok(saved);
    }

    // Get all documents of a donor
    @GetMapping("/{id}/documents")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable Long id) {
        List<Document> documents = donorService.getDocuments(id);
        return ResponseEntity.ok(documents);
    }

    // Delete donor (soft delete) — optional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
