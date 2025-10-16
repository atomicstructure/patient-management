package com.samantha.patientservice.service;

import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.model.Patient;
import com.samantha.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return
    }
}
