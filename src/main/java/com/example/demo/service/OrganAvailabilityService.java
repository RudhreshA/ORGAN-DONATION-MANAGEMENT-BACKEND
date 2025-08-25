package com.example.demo.service;

import com.example.demo.model.OrganAvailability;
import com.example.demo.repository.OrganAvailabilityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganAvailabilityService {

    private final OrganAvailabilityRepository organAvailabilityRepository;

    // Create a new organ availability
    public OrganAvailability createOrganAvailability(OrganAvailability organ) {
        return organAvailabilityRepository.save(organ);
    }

    // Update existing organ availability
    public OrganAvailability updateOrganAvailability(Long id, OrganAvailability organDetails) {
        OrganAvailability organ = getOrganAvailabilityById(id);
        organ.setOrganType(organDetails.getOrganType());
        organ.setBloodType(organDetails.getBloodType());
        organ.setAvailable(organDetails.isAvailable());
        organ.setOrganBank(organDetails.getOrganBank());
        return organAvailabilityRepository.save(organ);
    }

    // Get by ID
    public OrganAvailability getOrganAvailabilityById(Long id) {
        return organAvailabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organ availability not found with id: " + id));
    }

    // Get all
    public List<OrganAvailability> getAllOrganAvailability() {
        return organAvailabilityRepository.findAll();
    }

    // Delete
    public void deleteOrganAvailability(Long id) {
        organAvailabilityRepository.deleteById(id);
    }
}
