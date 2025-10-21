package com.samantha.patientservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;


@Builder
@Data
public class PatientResponseDTO {

    private String id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "email is required")
    @Email
    private String email;
    @NotBlank(message = "address is required")
    private String address;
    @NotNull(message = "dateOfBirth is required")
    private String dateOfBirth;

    @CreatedDate
    private String registeredDate;
}
