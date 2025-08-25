package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String organType; // e.g., Kidney, Heart

    private String bloodType;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "organ_bank_id")
    private OrganBank organBank;
}
