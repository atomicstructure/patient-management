package com.samantha.patientservice.controllers;


import com.samantha.patientservice.dto.PatientRequestDTO;
import com.samantha.patientservice.dto.PatientResponseDTO;
import com.samantha.patientservice.dto.validators.CreatePatientValidationGroup;
import com.samantha.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Tag(name = "Patient Controller", description = "API for Patient Management")
public class PatientController {

    public static final String PATIENT_PATH = "/api/v4/patients";
    public static final String PATIENT_ID_PATH = PATIENT_PATH + "/{patientId}";

    private final PatientService patientService;

    @GetMapping(value = PATIENT_PATH)
    @Operation(summary = "List all patients", description = "Returns a list of all patients in the system")
    public List<PatientResponseDTO> listPatients(){
        return patientService.listPatients();
    }

    @GetMapping(value = PATIENT_ID_PATH)
    @Operation(summary = "Get patient by ID", description = "Returns a single patient based on the provided ID")
    public Optional<PatientResponseDTO> getPatientById(@PathVariable("patientId") UUID patientId) {
        return Optional.of(patientService.getPatientById(patientId).orElseThrow(NotFoundException::new));
    }

    @PostMapping(value = PATIENT_PATH, consumes = "application/json")
    @Operation(summary = "Create a new patient", description = "Creates a new patient in the system and returns the location of the created resource")
    public ResponseEntity handlePost(@Validated({Default.class}) @RequestBody PatientRequestDTO patient) {
        PatientResponseDTO savedPatient = patientService.saveNewPatient(patient);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v4/patients/" + savedPatient.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = PATIENT_ID_PATH, consumes = "application/json")
    @Operation(summary = "Update an existing patient", description = "Updates an existing patient based on the provided ID and patient data. Returns no content if the update is successful")
    public ResponseEntity updatePatientById(@Validated({Default.class, CreatePatientValidationGroup.class})@PathVariable("patientId") UUID patientId, @Validated @RequestBody PatientRequestDTO patient){

        if (patientService.updatePatientById(patientId, patient).isEmpty())
            throw new NotFoundException();
        ;
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = PATIENT_ID_PATH, consumes = "application/json")
    @Operation(summary = "Partially update an existing patient", description = "Partially updates an existing patient based on the provided ID and patient data. Returns no content if the update is successful")
    public ResponseEntity patchPatientById(@PathVariable("patientId") UUID patientId,@Validated @RequestBody PatientRequestDTO patient) {
        patientService.patchPatientById(patientId, patient);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATIENT_ID_PATH)
    @Operation(summary = "Delete a patient", description = "Deletes an existing patient based on the provided ID. Returns no content if the deletion is successful")
    public  ResponseEntity deleteById(@PathVariable("patientId") UUID patientId){
        if (!patientService.deleteById(patientId)) {;
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
