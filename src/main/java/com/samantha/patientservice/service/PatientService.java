package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientRequestDTO;
import com.samantha.patientservice.dto.PatientResponseDTO;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PatientService {

    List<PatientResponseDTO> listPatients();

    Optional<PatientResponseDTO> getPatientById(UUID id);

    PatientResponseDTO saveNewPatient(PatientRequestDTO patient);

    Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientRequestDTO patient);

    boolean deleteById(UUID patientId);

    void patchPatientById(UUID patientId, PatientRequestDTO patient);
}