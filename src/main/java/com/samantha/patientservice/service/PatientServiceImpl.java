package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PatientServiceImpl implements PatientService {

    private Map<UUID, PatientResponseDTO> patientMap = new HashMap<>();

//    public PatientServiceImpl() {
//        this.patientMap = new HashMap<>();
//
//        // Sample data
//        PatientResponseDTO patient1 = PatientResponseDTO.builder()
//                .id(String.valueOf(UUID.randomUUID()))
//                .name("John Doe")
//                .email("johndoe@doe.com")
//                .build();
//        patientMap.put(UUID.fromString(patient1.getName()), patient1);
//    }


    @Override
    public List<PatientResponseDTO> listPatients(String name, String address, String email) {
        return new ArrayList<>(patientMap.values());
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
