package com.samantha.patientservice.mappers;

import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.model.Patient;


public class PatientMapper {

    public static PatientResponseDTO patientDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patientDTO.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patientDTO.getEmail());
        patientDTO.setDateOfBirth(patientDTO.getDateOfBirth());
        return patientDTO();
    }
}
