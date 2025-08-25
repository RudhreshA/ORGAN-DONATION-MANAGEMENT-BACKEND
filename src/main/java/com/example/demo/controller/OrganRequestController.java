package com.example.demo.controller;

import com.example.demo.dto.OrganRequestDTO;
import com.example.demo.dto.OrganRequestResponse;
import com.example.demo.model.Hospital;
import com.example.demo.model.OrganRequest;
import com.example.demo.model.RequestStatus;
import com.example.demo.repository.HospitalRepository;
import com.example.demo.repository.OrganRequestRepository;
import com.example.demo.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organ-requests")
@RequiredArgsConstructor
public class OrganRequestController {

    private final OrganRequestRepository organRequestRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalService hospitalService;

    // ----------------- GET ALL -----------------
    @GetMapping
    public List<OrganRequestDTO> getAllRequests() {
        return organRequestRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ----------------- GET BY ID -----------------
@GetMapping("/by-hospital-user/{userId}")
public ResponseEntity<List<OrganRequestDTO>> getRequestsByHospitalUser(@PathVariable Long userId) {
    Hospital hospital = hospitalRepository.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("Hospital not found for user id: " + userId));

    List<OrganRequestDTO> dtos = organRequestRepository.findByHospitalId(hospital.getId())
        .stream().map(this::mapToDTO).toList();

    return ResponseEntity.ok(dtos);
}

    @GetMapping("/by-user/{userId}")
public List<OrganRequest> getRequestsByUser(@PathVariable Long userId) {
    Hospital hospital = hospitalRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Hospital not found for user id: " + userId));

    return organRequestRepository.findByHospital(hospital);
}

    @GetMapping("/hospital/{hospitalId}")
public ResponseEntity<List<OrganRequestResponse>> getRequestsByHospital(@PathVariable Long hospitalId) {
    List<OrganRequest> requests = organRequestRepository.findByHospitalId(hospitalId);

    List<OrganRequestResponse> response = requests.stream()
            .map(r -> new OrganRequestResponse(
                    r.getId(),
                    r.getOrganType(),
                    r.getStatus(),
                    r.getHospital().getId(),
                    r.getOrganBank() != null ? r.getOrganBank().getId() : null
            ))
            .toList();

         return ResponseEntity.ok(response);
    }

    // ----------------- CREATE -----------------
@PostMapping
public ResponseEntity<?> createRequest(@RequestBody OrganRequestRequest request) {
    OrganRequest organRequest = new OrganRequest();
    organRequest.setOrganType(request.getOrganType());

    if (request.getHospitalId() != null) {
        Hospital hospital = hospitalService.getHospitalEntityById(request.getHospitalId()); // ✅ hospitalId, not userId
        organRequest.setHospital(hospital);
    }

    OrganRequest saved = organRequestRepository.save(organRequest);
    return ResponseEntity.ok(saved);
}


    // ----------------- UPDATE -----------------
    @PutMapping("/{id}")
    public ResponseEntity<OrganRequestDTO> updateRequest(@PathVariable Long id, @RequestBody OrganRequest details) {
        return organRequestRepository.findById(id).map(req -> {
            req.setOrganType(details.getOrganType());
            req.setStatus(details.getStatus());
            if (details.getHospital() != null && details.getHospital().getId() != null) {
                req.setHospital(hospitalService.getHospitalEntityById(details.getHospital().getId()));
            }
            req.setOrganBank(details.getOrganBank());
            OrganRequest updated = organRequestRepository.save(req);
            return ResponseEntity.ok(mapToDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ----------------- PATCH STATUS -----------------
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrganRequestDTO> updateStatus(@PathVariable Long id,
                                                        @RequestBody StatusUpdateRequest statusRequest) {
        return organRequestRepository.findById(id).map(req -> {
            req.setStatus(statusRequest.getStatus());
            OrganRequest updated = organRequestRepository.save(req);
            return ResponseEntity.ok(mapToDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ----------------- Helper -----------------
    private OrganRequestDTO mapToDTO(OrganRequest req) {
        return OrganRequestDTO.builder()
                .id(req.getId())
                .organType(req.getOrganType())
                .status(req.getStatus())
                .hospitalId(req.getHospital() != null ? req.getHospital().getId() : null)
                .organBankId(req.getOrganBank() != null ? req.getOrganBank().getId() : null)
                .build();
    }

    // ----------------- DTOs -----------------
    public static class StatusUpdateRequest {
        private RequestStatus status;
        public RequestStatus getStatus() { return status; }
        public void setStatus(RequestStatus status) { this.status = status; }
    }

    public static class OrganRequestRequest {
        private String organType;
        private Long hospitalId;
        public String getOrganType() { return organType; }
        public void setOrganType(String organType) { this.organType = organType; }
        public Long getHospitalId() { return hospitalId; }
        public void setHospitalId(Long hospitalId) { this.hospitalId = hospitalId; }
    }
}
