package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final DonorRepository donorRepository;
    private final HospitalRepository hospitalRepository;
    private final OrganBankRepository organBankRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user first
        User savedUser = userRepository.save(user);

        // Create role-specific records
        if (user.getRole() == Role.DONOR && user.getDonor() != null) {
            user.getDonor().setUser(savedUser);
            donorRepository.save(user.getDonor());
        }
        else if (user.getRole() == Role.HOSPITAL_STAFF && user.getHospital() != null) {
            user.getHospital().setUser(savedUser);
            hospitalRepository.save(user.getHospital());
        }
        else if (user.getRole() == Role.ORGAN_BANK_STAFF && user.getOrganBank() != null) {
            OrganBank organBank = organBankRepository.save(user.getOrganBank());
            savedUser.setOrganBank(organBank);
        }

        return savedUser;
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        user.setDeleted(true);
        userRepository.save(user);
    }
}
