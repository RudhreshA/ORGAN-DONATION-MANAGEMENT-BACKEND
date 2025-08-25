package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrganAvailability;

@Repository
public interface OrganAvailabilityRepository extends JpaRepository<OrganAvailability, Long>{

}
