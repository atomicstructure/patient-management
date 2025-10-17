package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientResponseDTO;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class PatientServiceImpl implements PatientService {

    private Map<UUID, PatientResponseDTO> patientMap;

//    public PatientServiceImpl(Map<UUID, PatientResponseDTO> patientMap) {
//        this.patientMap = patientMap;
//    }


    @Override
    public Page<PatientResponseDTO> listPatients(String name, String address, String email, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(patientMap.values()));
    }

    @Override
    public Optional<PatientResponseDTO> getPatientById(UUID id) {
        PatientResponseDTO patientResponseDTO = patientMap.get(id);
        if (patientResponseDTO != null) {
            return Optional.of(patientResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    public PatientResponseDTO saveNewPatient(PatientResponseDTO patient) {
        PatientResponseDTO savedPatient = PatientResponseDTO.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth())
                .registeredDate(patient.getRegisteredDate())
                .build();
        patientMap.put(UUID.fromString(savedPatient.getId()), savedPatient);
        return savedPatient;
    }

    @Override
    public Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientResponseDTO patient) {

        PatientResponseDTO patientResponseDTO = patientMap.get(patientId);
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        return Optional.of(patientResponseDTO);
    }

    @Override
    public boolean deleteById(UUID patientId) {
        patientMap.remove(patientId);
        return false;
    }

    @Override
    public void patchPatientById(UUID patientId, PatientResponseDTO patient) {
        PatientResponseDTO existingPatient = patientMap.get(patientId);

        boolean updated = false;

        if (existingPatient != null) {
            if (patient.getName() != null) {
                existingPatient.setName(patient.getName());
                updated = true;
            }
        }
        if (updated) {
            patientMap.put(patientId, existingPatient);
        }

    }
}
