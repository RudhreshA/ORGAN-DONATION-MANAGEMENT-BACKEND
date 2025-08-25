package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrganBank;

@Repository
public interface OrganBankRepository extends JpaRepository<OrganBank, Long>{

}
