package com.example.demo.controller;

import com.example.demo.dto.HospitalResponseDTO;
import com.example.demo.model.Hospital;
import com.example.demo.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    // Create a new hospital
    @PostMapping
    public ResponseEntity<HospitalResponseDTO> createHospital(@RequestBody Hospital hospital) {
        return ResponseEntity.ok(hospitalService.createHospital(hospital));
    }


     @GetMapping("/user/{userId}")
    public ResponseEntity<HospitalResponseDTO> getHospitalByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(hospitalService.getHospitalByUserId(userId));
    }

    // Get hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> getHospitalById(@PathVariable Long id) {
        return ResponseEntity.ok(hospitalService.getHospitalById(id));
    }

    @PutMapping("/{id}")
public ResponseEntity<HospitalResponseDTO> updateHospital(
        @PathVariable Long id, 
        @RequestBody HospitalResponseDTO hospitalDTO) {

    return ResponseEntity.ok(hospitalService.updateHospitalFromDTO(id, hospitalDTO));
}


    // Get all hospitals
    @GetMapping
    public ResponseEntity<List<HospitalResponseDTO>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    // Delete hospital
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }
}
