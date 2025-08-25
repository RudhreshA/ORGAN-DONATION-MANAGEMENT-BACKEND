package com.example.demo.service;

import com.example.demo.model.OrganRequest;
import com.example.demo.repository.OrganRequestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganRequestService {

    private final OrganRequestRepository organRequestRepository;

    // Create a new organ request
    public OrganRequest createOrganRequest(OrganRequest request) {
        return organRequestRepository.save(request);
    }

    // Update an existing organ request
    public OrganRequest updateOrganRequest(Long id, OrganRequest requestDetails) {
        OrganRequest request = getOrganRequestById(id);
        request.setOrganType(requestDetails.getOrganType());
        request.setStatus(requestDetails.getStatus());
        request.setHospital(requestDetails.getHospital());
        request.setOrganBank(requestDetails.getOrganBank());
        return organRequestRepository.save(request);
    }

    // Get by ID
    public OrganRequest getOrganRequestById(Long id) {
        return organRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organ request not found with id: " + id));
    }

    // Get all organ requests
    public List<OrganRequest> getAllOrganRequests() {
        return organRequestRepository.findAll();
    }

    // Delete organ request
    public void deleteOrganRequest(Long id) {
        organRequestRepository.deleteById(id);
    }
}
