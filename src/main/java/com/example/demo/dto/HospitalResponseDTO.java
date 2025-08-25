package com.example.demo.dto;

import com.example.demo.model.Hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String contactNumber;

     public HospitalResponseDTO(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.contactNumber = hospital.getContactNumber();
    }
}
