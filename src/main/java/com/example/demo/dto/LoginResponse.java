package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {

    @JsonProperty("userId")   // Ensures JSON response has "userId"
    private Long userId;

    private String role;
    private String token;

    public LoginResponse(Long userId, String role, String token) {
    this.userId = userId;
    this.role = role;
    this.token = token;
}
}
