package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientResponseDTO;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PatientService {

    List<PatientResponseDTO> listPatients(String name, String address, String email, Integer pageNumber, Integer pageSize);

    Optional<PatientResponseDTO> getPatientById(UUID id);

    PatientResponseDTO saveNewPatient(PatientResponseDTO patient);

    Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientResponseDTO patient);

    boolean deleteById(UUID patientId);

    void patchPatientById(UUID patientId, PatientResponseDTO patient);
}