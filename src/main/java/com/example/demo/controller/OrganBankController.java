package com.example.demo.controller;

import com.example.demo.model.OrganBank;
import com.example.demo.service.OrganBankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organ-banks")
@RequiredArgsConstructor
public class OrganBankController {

    private final OrganBankService organBankService;

    // Create a new organ bank
    @PostMapping
    public ResponseEntity<OrganBank> createOrganBank(@Valid @RequestBody OrganBank organBank) {
        OrganBank createdBank = organBankService.createOrganBank(organBank);
        return ResponseEntity.ok(createdBank);
    }

    // Get organ bank by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganBank> getOrganBankById(@PathVariable Long id) {
        OrganBank organBank = organBankService.getOrganBankById(id);
        return ResponseEntity.ok(organBank);
    }

    // Update organ bank
    @PutMapping("/{id}")
    public ResponseEntity<OrganBank> updateOrganBank(
            @PathVariable Long id,
            @Valid @RequestBody OrganBank bankDetails) {
        OrganBank updatedBank = organBankService.updateOrganBank(id, bankDetails);
        return ResponseEntity.ok(updatedBank);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF', 'ORGAN_BANK_STAFF')")
    public ResponseEntity<List<OrganBank>> getAllOrganBanks() {
        List<OrganBank> banks = organBankService.getAllOrganBanks();
        return ResponseEntity.ok(banks);
    }

    // Delete organ bank
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganBank(@PathVariable Long id) {
        organBankService.deleteOrganBank(id);
        return ResponseEntity.noContent().build();
    }
}
