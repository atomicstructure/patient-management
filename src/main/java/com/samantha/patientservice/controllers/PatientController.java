package com.samantha.patientservice.controllers;


import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class PatientController {

    public static final String PATIENT_PATH = "/api/v1/patients";
    public static final String PATIENT_ID_PATH = "/api/v1/patients/{patientId}";

    private final PatientService patientService;

    @GetMapping(value = PATIENT_PATH)
    public List<PatientResponseDTO> listPatients(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String address,
                                                 @RequestParam(required = false) String email
                                                 ){
        return patientService.listPatients(name, address, email);
    }

    @GetMapping(value = PATIENT_ID_PATH)
    public PatientResponseDTO getPatientById(@PathVariable("patientId") UUID patientId) throws ChangeSetPersister.NotFoundException {
        return patientService.getPatientById(patientId).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PostMapping(value = PATIENT_PATH, consumes = "application/json")
    public ResponseEntity handlePost(@Validated @RequestBody PatientResponseDTO patient) {
        PatientResponseDTO savedPatient = patientService.saveNewPatient(patient);

        HttpHeaders headers = new HttpHeaders();
        if (savedPatient.getId() != null) {
            headers.add("Location", PATIENT_PATH + "/" + savedPatient.getId().toString());
        } else {
            UUID generatedId = UUID.randomUUID();
            headers.add("Location", PATIENT_PATH + "/" + generatedId.toString());
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = PATIENT_ID_PATH, consumes = "application/json")
    public ResponseEntity updatePatientById(@PathVariable("patientId") UUID patientId, @Validated @RequestBody PatientResponseDTO patient) throws ChangeSetPersister.NotFoundException {

        if (patientService.updatePatientById(patientId, patient).isEmpty())
            throw new ChangeSetPersister.NotFoundException();
        ;
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = PATIENT_ID_PATH, consumes = "application/json")
    public ResponseEntity patchPatientById(@PathVariable("patientId") UUID patientId,@Validated @RequestBody PatientResponseDTO patient) {
        patientService.patchPatientById(patientId, patient);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATIENT_ID_PATH)
    public  ResponseEntity deleteById(@PathVariable("patientId") UUID patientId) throws ChangeSetPersister.NotFoundException {
        if (!patientService.deleteById(patientId)) {;
            throw new ChangeSetPersister.NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
