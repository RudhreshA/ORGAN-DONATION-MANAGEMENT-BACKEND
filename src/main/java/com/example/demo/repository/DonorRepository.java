package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByUserId(Long userId);

}
