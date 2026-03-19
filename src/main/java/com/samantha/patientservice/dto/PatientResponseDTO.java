package com.samantha.patientservice.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Builder
@Data
public class PatientResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
    private String registeredDate;
}
