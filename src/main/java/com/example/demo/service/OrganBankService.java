package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.OrganBank;
import com.example.demo.repository.OrganBankRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganBankService {

    private final OrganBankRepository organBankRepository;

    // Create new organ bank
    public OrganBank createOrganBank(OrganBank organBank) {
        return organBankRepository.save(organBank);
    }

    // Update existing organ bank
    public OrganBank updateOrganBank(Long id, OrganBank bankDetails) {
        OrganBank organBank = getOrganBankById(id);
        organBank.setName(bankDetails.getName());
        organBank.setAddress(bankDetails.getAddress());
        organBank.setContactNumber(bankDetails.getContactNumber()); // ensure contact is updated
        return organBankRepository.save(organBank);
    }

    // Get organ bank by ID
    public OrganBank getOrganBankById(Long id) {
        return organBankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organ bank not found with id: " + id));
    }

    // Get all organ banks
    public List<OrganBank> getAllOrganBanks() {
        return organBankRepository.findAll();
    }

    // Delete organ bank
    public void deleteOrganBank(Long id) {
        // NOTE: With current cascade settings (PERSIST, MERGE), this won't cascade delete users/availability.
        organBankRepository.deleteById(id);
    }
}
