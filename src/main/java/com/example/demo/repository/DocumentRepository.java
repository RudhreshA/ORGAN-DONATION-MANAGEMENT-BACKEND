package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Document;
import com.example.demo.model.Donor;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
    List<Document> findByDonor(Donor donor);
}
