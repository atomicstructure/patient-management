package com.samantha.patientservice.service;


import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.mappers.PatientMapper;
import com.samantha.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PatientServiceJPA implements PatientService{
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    @Override
    public List<PatientResponseDTO> listPatients(String name, String address, String email) {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::patientToPatientResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PatientResponseDTO> getPatientById(UUID id) {
        return Optional.ofNullable(patientMapper
                .patientToPatientResponseDTO(patientRepository.findById(id).orElse(null)));
    }

    @Override
    public PatientResponseDTO saveNewPatient(PatientResponseDTO patient) {
        return patientMapper.patientToPatientResponseDTO(patientRepository.save(patientMapper.patientResponseDTOToPatient(patient)));
    }

    @Override
    public Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientResponseDTO patient) {

        AtomicReference<Optional<PatientResponseDTO>> atomicReference = new AtomicReference<>();

        patientRepository.findById(patientId).ifPresentOrElse( patientEntity ->{
            patientEntity.setName(patient.getName());
            patientEntity.setAddress(patient.getAddress());
            patientEntity.setEmail(patient.getEmail());
            patientEntity.setRegisteredDate(LocalDate.parse(patient.getRegisteredDate()));
            patientEntity.setDateOfBirth(LocalDate.parse(patient.getDateOfBirth()));
            atomicReference.set(Optional.of(patientMapper
                    .patientToPatientResponseDTO(patientRepository.save(patientEntity))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public boolean deleteById(UUID patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
            return true;
        }
        return false;
    }

    @Override
    public void patchPatientById(UUID patientId, PatientResponseDTO patient) {

    }
}
