package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    // Upload a new document
    public Document uploadDocument(Document document) {
        return documentRepository.save(document);
    }

    // Update existing document
    public Document updateDocument(Long id, Document documentDetails) {
        Document document = getDocumentById(id);
        document.setFileName(documentDetails.getFileName());
        document.setFileType(documentDetails.getFileType());
        document.setFilePath(documentDetails.getFilePath());
        document.setDonor(documentDetails.getDonor()); // corrected
        return documentRepository.save(document);
    }

    // Get document by ID
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    // Get all documents
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // Delete document
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
