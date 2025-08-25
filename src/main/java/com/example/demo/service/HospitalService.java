package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.HospitalResponseDTO;
import com.example.demo.model.Hospital;
import com.example.demo.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    // Create a new hospital
    public HospitalResponseDTO createHospital(Hospital hospital) {
        Hospital saved = hospitalRepository.save(hospital);
        return mapToDTO(saved);
    }

    public HospitalResponseDTO getHospitalByUserId(Long userId) {
        Hospital hospital = hospitalRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Hospital not found for user id: " + userId));
        return new HospitalResponseDTO(hospital);
    }

    // Update existing hospital
    public HospitalResponseDTO updateHospitalFromDTO(Long id, HospitalResponseDTO dto) {
    Hospital hospital = hospitalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));

    hospital.setName(dto.getName());
    hospital.setAddress(dto.getAddress());
    hospital.setContactNumber(dto.getContactNumber());

    Hospital updated = hospitalRepository.save(hospital);
    return mapToDTO(updated);
}


    // Get hospital DTO by ID
    public HospitalResponseDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
        return mapToDTO(hospital);
    }

    // Get hospital entity by ID (for internal use)
    public Hospital getHospitalEntityById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    // Get all hospitals DTO
    public List<HospitalResponseDTO> getAllHospitals() {
        return hospitalRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Delete hospital
    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }

    // ----------------- Helper -----------------
    private HospitalResponseDTO mapToDTO(Hospital hospital) {
        return HospitalResponseDTO.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .contactNumber(hospital.getContactNumber())
                .build();
    }
}
