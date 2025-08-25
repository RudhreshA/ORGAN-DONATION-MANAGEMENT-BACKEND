package com.example.demo.controller;

import com.example.demo.model.OrganAvailability;
import com.example.demo.service.OrganAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organ-availability")
@RequiredArgsConstructor
public class OrganAvailabilityController {

    private final OrganAvailabilityService organAvailabilityService;

    // Create a new organ availability
    @PostMapping
    public ResponseEntity<OrganAvailability> createOrganAvailability(@RequestBody OrganAvailability organ) {
        OrganAvailability createdOrgan = organAvailabilityService.createOrganAvailability(organ);
        return ResponseEntity.ok(createdOrgan);
    }

    // Get organ availability by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrganAvailability> getOrganAvailabilityById(@PathVariable Long id) {
        OrganAvailability organ = organAvailabilityService.getOrganAvailabilityById(id);
        return ResponseEntity.ok(organ);
    }

    // Update organ availability
    @PutMapping("/{id}")
    public ResponseEntity<OrganAvailability> updateOrganAvailability(
            @PathVariable Long id,
            @RequestBody OrganAvailability organDetails) {
        OrganAvailability updatedOrgan = organAvailabilityService.updateOrganAvailability(id, organDetails);
        return ResponseEntity.ok(updatedOrgan);
    }

    // Get all organ availability entries
    @GetMapping
    public ResponseEntity<List<OrganAvailability>> getAllOrganAvailability() {
        List<OrganAvailability> organs = organAvailabilityService.getAllOrganAvailability();
        return ResponseEntity.ok(organs);
    }

    // Delete organ availability
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganAvailability(@PathVariable Long id) {
        organAvailabilityService.deleteOrganAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
