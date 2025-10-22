package com.samantha.patientservice.mappers;

import com.samantha.patientservice.dto.PatientRequestDTO;
import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PatientMapper {

    @Mapping(target = "registeredDate", ignore = true)
    Patient patientResponseDTOToPatient(PatientRequestDTO dto);

    PatientResponseDTO patientToPatientResponseDTO(Patient patient);

}
