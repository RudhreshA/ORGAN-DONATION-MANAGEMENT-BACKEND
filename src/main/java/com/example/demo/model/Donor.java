package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(18)
    private int age;

    @Column(nullable = true)
    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$")
    private String bloodGroup;

    @Column(nullable = true)
    private String organList;

    private String consentStatus;

    @Column(length = 2000, nullable = true)
    private String medicalInfo;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Document> documents;
}
