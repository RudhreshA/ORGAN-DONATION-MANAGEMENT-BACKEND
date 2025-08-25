package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String role; // ✅ String, we’ll normalize to enum

    // Donor-specific
    private Integer age;       // optional but useful
    private String bloodGroup;
    private String organList;
    private String medicalInfo;

    // Hospital-specific
    private String hospitalName;
    private String address;
    private String contactNumber;

    // OrganBank-specific
    private String organBankName;
}
