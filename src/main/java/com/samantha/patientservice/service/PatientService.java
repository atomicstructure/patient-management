package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientResponseDTO;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PatientService {

    List<PatientResponseDTO> listPatients(String name, String address, String email);

    Optional<PatientResponseDTO> getPatientById(UUID id);

    PatientResponseDTO saveNewPatient(PatientResponseDTO patient);

    Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientResponseDTO patient);

    boolean deleteById(UUID patientId);

    void patchPatientById(UUID patientId, PatientResponseDTO patient);
}