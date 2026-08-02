package com.medcare.controller;

import com.medcare.dto.PatientDTO;
import com.medcare.security.JwtProvider;
import com.medcare.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Autowired
    private JwtProvider jwtProvider;

    private String authToken;

    @BeforeEach
    void setUp() {
        authToken = "Bearer " + jwtProvider.generateToken(1L, "admin@medcare.com", "ADMIN");
    }

    @Test
    void findAll_shouldReturnPatients() throws Exception {
        PatientDTO dto = new PatientDTO();
        dto.setId(1L);
        dto.setNom("DIA");
        dto.setPrenom("Oumar");
        dto.setTelephone("770000000");

        List<PatientDTO> patients = Arrays.asList(dto);
        when(patientService.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients")
                        .header("Authorization", authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("DIA"))
                .andExpect(jsonPath("$[0].prenom").value("Oumar"))
                .andExpect(jsonPath("$[0].telephone").value("770000000"));
    }

    @Test
    void findById_shouldReturnPatient() throws Exception {
        PatientDTO dto = new PatientDTO();
        dto.setId(1L);
        dto.setNom("DIA");
        dto.setPrenom("Oumar");

        when(patientService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/patients/1")
                        .header("Authorization", authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("DIA"));
    }

    @Test
    void create_shouldCreatePatient() throws Exception {
        PatientDTO dto = new PatientDTO();
        dto.setNom("DIA");
        dto.setPrenom("Oumar");
        dto.setTelephone("770000000");
        dto.setDateNaissance(LocalDate.of(1990, 1, 15));

        PatientDTO saved = new PatientDTO();
        saved.setId(1L);
        saved.setNom("DIA");
        saved.setPrenom("Oumar");

        when(patientService.create(any(PatientDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/patients")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("DIA"));
    }

    @Test
    void update_shouldUpdatePatient() throws Exception {
        PatientDTO dto = new PatientDTO();
        dto.setNom("DIA");
        dto.setPrenom("Oumar Updated");
        dto.setTelephone("771111111");

        when(patientService.update(eq(1L), any(PatientDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/patients/1")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom").value("Oumar Updated"));
    }

    @Test
    void delete_shouldDeletePatient() throws Exception {
        doNothing().when(patientService).delete(1L);

        mockMvc.perform(delete("/api/patients/1")
                        .header("Authorization", authToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAll_shouldReturn403WithoutToken() throws Exception {
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isForbidden());
    }
}
