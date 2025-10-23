package com.samantha.patientservice.service;


import com.samantha.patientservice.dto.PatientRequestDTO;
import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.exception.EmailAlreadyExistsException;
import com.samantha.patientservice.exception.PatientNotFoundException;
import com.samantha.patientservice.mappers.PatientMapper;
import com.samantha.patientservice.model.Patient;
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
    public List<PatientResponseDTO> listPatients() {
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
    public PatientResponseDTO saveNewPatient(PatientRequestDTO patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + patient.getEmail());
        }
        // 1. Map DTO to Entity (registeredDate will be null or ignored by mapper)
        Patient patientEntity = patientMapper.patientResponseDTOToPatient(patient);

        // 2. IMPORTANT: Set the server-side generated registeredDate before saving.
        // This satisfies the @NotNull constraint in Patient.java.
        patientEntity.setRegisteredDate(LocalDate.now());

        // 3. Save the completed entity
        return patientMapper.patientToPatientResponseDTO(patientRepository.save(patientEntity));
    }

    @Override
    public Optional<PatientResponseDTO> updatePatientById(UUID patientId, PatientResponseDTO patient) {

        AtomicReference<Optional<PatientResponseDTO>> atomicReference = new AtomicReference<>();

        patientRepository.findById(patientId).ifPresentOrElse( patientEntity ->{
            patientEntity.setName(patient.getName());
            patientEntity.setAddress(patient.getAddress());
            patientEntity.setEmail(patient.getEmail());
            patientEntity.setRegisteredDate(LocalDate.now());
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
