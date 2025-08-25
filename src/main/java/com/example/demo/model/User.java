package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @JsonIgnore 
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean isDeleted = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Donor donor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "organ_bank_id")
    private OrganBank organBank;
}
