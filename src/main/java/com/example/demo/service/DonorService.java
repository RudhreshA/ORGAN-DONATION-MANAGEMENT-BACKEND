package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.model.Donor;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.DonorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DonorService {

    private final DonorRepository donorRepository;
    private final DocumentRepository documentRepository;

    // ✅ Create donor
    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    // Get donor by ID
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + id));
    }

    // Update donor details
    public Donor updateDonor(Long id, Donor donorDetails) {
        Donor donor = getDonorById(id);
        donor.setName(donorDetails.getName());
        donor.setAge(donorDetails.getAge());
        donor.setBloodGroup(donorDetails.getBloodGroup());
        donor.setOrganList(donorDetails.getOrganList());
        donor.setConsentStatus(donorDetails.getConsentStatus());
        donor.setMedicalInfo(donorDetails.getMedicalInfo());
        return donorRepository.save(donor);
    }

    public String getConsentStatus(Long id) {
        Donor donor = getDonorById(id);
        return donor.getConsentStatus();
    }

    public Donor updateConsentStatus(Long id, String status) {
        Donor donor = getDonorById(id);
        donor.setConsentStatus(status);
        return donorRepository.save(donor);
    }

    public Document uploadDocument(Long donorId, String fileName, String fileType, String filePath) {
        Donor donor = getDonorById(donorId);
        Document document = new Document();
        document.setDonor(donor);
        document.setFileName(fileName);
        document.setFileType(fileType);
        document.setFilePath(filePath);
        return documentRepository.save(document);
    }

    public List<Document> getDocuments(Long donorId) {
        Donor donor = getDonorById(donorId);
        return documentRepository.findByDonor(donor);
    }

    public Donor getDonorByUserId(Long userId) {
    return donorRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Donor not found for user " + userId));
}

}
