package com.medcare.mapper;

import com.medcare.dto.PatientDTO;
import com.medcare.entity.Patient;
import com.medcare.entity.Sexe;

public class PatientMapper {

    public static PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setNom(patient.getNom());
        dto.setPrenom(patient.getPrenom());
        dto.setDateNaissance(patient.getDateNaissance());
        dto.setSexe(patient.getSexe() != null ? patient.getSexe().name() : null);
        dto.setTelephone(patient.getTelephone());
        dto.setAdresse(patient.getAdresse());
        dto.setGroupeSanguin(patient.getGroupeSanguin());
        dto.setEmail(patient.getEmail());
        return dto;
    }

    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setDateNaissance(dto.getDateNaissance());
        if (dto.getSexe() != null && !dto.getSexe().isEmpty()) {
            patient.setSexe(Sexe.valueOf(dto.getSexe()));
        }
        patient.setTelephone(dto.getTelephone());
        patient.setAdresse(dto.getAdresse());
        patient.setGroupeSanguin(dto.getGroupeSanguin());
        patient.setEmail(dto.getEmail());
        return patient;
    }
}
