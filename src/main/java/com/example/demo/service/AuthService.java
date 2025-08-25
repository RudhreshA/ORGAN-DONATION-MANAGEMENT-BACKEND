package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final DonorRepository donorRepository;
    private final HospitalRepository hospitalRepository;
    private final OrganBankRepository organBankRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ---------- REGISTER ----------
    public UserResponse register(RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already registered");
    }

    // Create User
    User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.valueOf(request.getRole()))
            .build();

    User savedUser = userRepository.save(user);

    // Save role-specific details
    switch (savedUser.getRole()) {
        case DONOR -> {
            Donor donor = Donor.builder()
                    .user(savedUser)
                    .name(request.getName())            // ✅ Set name to satisfy @NotBlank
                    .age(request.getAge())
                    .bloodGroup(request.getBloodGroup())
                    .organList(request.getOrganList())
                    .medicalInfo(request.getMedicalInfo())
                    .build();
            
            // Save donor first
            donorRepository.save(donor);

            // Link donor back to user
            savedUser.setDonor(donor);
            userRepository.save(savedUser);
        }
        case HOSPITAL_STAFF -> {
            Hospital hospital = Hospital.builder()
                    .user(savedUser)
                    .name(request.getHospitalName())
                    .address(request.getAddress())
                    .contactNumber(request.getContactNumber())
                    .build();
            hospitalRepository.save(hospital);
        }
        case ORGAN_BANK_STAFF -> {
            OrganBank bank = OrganBank.builder()
                    .name(request.getOrganBankName())
                    .address(request.getAddress())
                    .contactNumber(request.getContactNumber())
                    .build();

            // Link the user
            bank.setUsers(List.of(savedUser));
            organBankRepository.save(bank);
        }
        case ADMIN -> throw new IllegalArgumentException("Admins cannot self-register. Use registerAdmin().");
        default -> throw new IllegalArgumentException("Unsupported role: " + savedUser.getRole());
    }

    return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
}


    // ---------- REGISTER ADMIN ----------
    public UserResponse registerAdmin(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        User admin = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        User saved = userRepository.save(admin);
        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole());
    }

    // ---------- LOGIN ----------
    public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid credentials");
    }

    String requestedRole = request.getRole().replace("ROLE_", "");
    if (!user.getRole().name().equalsIgnoreCase(requestedRole)) {
        throw new IllegalArgumentException("Role mismatch for this user");
    }

    String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

    // ✅ return LoginResponse with correct argument order
    return new LoginResponse(user.getId(), user.getRole().name(), token);
}

}
