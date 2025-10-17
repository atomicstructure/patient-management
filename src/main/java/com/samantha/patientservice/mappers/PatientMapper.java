package com.samantha.patientservice.mappers;

import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.model.Patient;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper {

    Patient patientResponseDTOToPatient(PatientResponseDTO dto);

    PatientResponseDTO patientToPatientResponseDTO(Patient patient);

}
