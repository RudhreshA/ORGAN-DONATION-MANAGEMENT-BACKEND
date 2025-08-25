package com.example.demo.dto;

import com.example.demo.model.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganRequestResponse {
    private Long id;
    private String organType;
    private RequestStatus status;
    private Long hospitalId;
    private Long organBankId;
}

