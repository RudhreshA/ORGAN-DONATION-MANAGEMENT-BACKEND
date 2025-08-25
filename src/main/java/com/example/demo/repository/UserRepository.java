package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsDeletedFalse();  // For soft delete filtering
    Optional<User> findByEmail(String email); // For login
    boolean existsByEmail(String email); // For registration check
    Optional<User> findByEmailIgnoreCase(String email);
    
    boolean existsByEmailIgnoreCase(String email);
}
