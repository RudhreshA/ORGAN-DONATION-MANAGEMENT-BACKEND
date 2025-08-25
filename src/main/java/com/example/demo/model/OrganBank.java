package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Organ bank name is required")
    @Size(max = 150, message = "Name must be at most 150 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 250, message = "Address must be at most 250 characters")
    private String address;

    @NotBlank(message = "Contact number is required")
    @Size(max = 30, message = "Contact number must be at most 30 characters")
    private String contactNumber;

    /**
     * One organ bank can have multiple users.
     * Use PERSIST and MERGE to avoid accidental deletes cascading to users.
     * Change to CascadeType.ALL only if you explicitly want deletes to cascade.
     */
    @OneToMany(mappedBy = "organBank", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users;

    /**
     * Same rationale for availability records.
     */
    @OneToMany(mappedBy = "organBank", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrganAvailability> availabilityList;
}
