package com.medcare.service;

import com.medcare.dto.PatientDTO;
import com.medcare.entity.Patient;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.repository.PatientRepository;
import com.medcare.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setNom("DIA");
        testPatient.setPrenom("Oumar");
        testPatient.setDateNaissance(LocalDate.of(1990, 1, 15));
        testPatient.setSexe(com.medcare.entity.Sexe.M);
        testPatient.setTelephone("770000000");
        testPatient.setEmail("dia@test.com");
    }

    @Test
    void findAll_shouldReturnAllPatients() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(testPatient));

        List<PatientDTO> result = patientService.findAll();

        assertEquals(1, result.size());
        assertEquals("DIA", result.get(0).getNom());
        assertEquals("Oumar", result.get(0).getPrenom());
    }

    @Test
    void findById_shouldReturnPatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));

        PatientDTO result = patientService.findById(1L);

        assertNotNull(result);
        assertEquals("DIA", result.getNom());
        assertEquals("Oumar", result.getPrenom());
        assertEquals("770000000", result.getTelephone());
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.findById(99L));
    }

    @Test
    void create_shouldCreatePatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("DIA");
        dto.setPrenom("Oumar");
        dto.setTelephone("770000000");

        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        PatientDTO result = patientService.create(dto);

        assertNotNull(result);
        assertEquals("DIA", result.getNom());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void update_shouldUpdatePatient() {
        PatientDTO dto = new PatientDTO();
        dto.setNom("DIA");
        dto.setPrenom("Oumar");
        dto.setTelephone("771111111");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(testPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        PatientDTO result = patientService.update(1L, dto);

        assertNotNull(result);
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void delete_shouldDeletePatient() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        patientService.delete(1L);

        verify(patientRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowWhenNotFound() {
        when(patientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patientService.delete(99L));
    }
}
