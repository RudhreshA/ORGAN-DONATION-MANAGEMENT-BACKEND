package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    // Upload a new document
    @PostMapping
    public ResponseEntity<Document> uploadDocument(@RequestBody Document document) {
        Document uploadedDocument = documentService.uploadDocument(document);
        return ResponseEntity.ok(uploadedDocument);
    }

    // Update an existing document
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document document) {
        Document updatedDocument = documentService.updateDocument(id, document);
        return ResponseEntity.ok(updatedDocument);
    }

    // Get a document by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    // Get all documents
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // Delete a document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
