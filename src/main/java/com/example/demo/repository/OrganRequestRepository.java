package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Hospital;
import com.example.demo.model.OrganRequest;

@Repository
public interface OrganRequestRepository extends JpaRepository<OrganRequest, Long>{
        List<OrganRequest> findByHospitalId(Long hospitalId);
         List<OrganRequest> findByHospital(Hospital hospital);
}
