package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String address;

    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hospital", "donor"}) // prevent infinite recursion
    private User user;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hospital", "organBank"}) // prevent infinite recursion
    private List<OrganRequest> organRequests;
}
