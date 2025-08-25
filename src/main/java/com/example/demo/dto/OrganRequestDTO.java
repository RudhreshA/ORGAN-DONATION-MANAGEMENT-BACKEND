package com.example.demo.dto;

import com.example.demo.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganRequestDTO {
    private Long id;
    private String organType;
    private RequestStatus status;
    private Long hospitalId;
    private Long organBankId;
}
