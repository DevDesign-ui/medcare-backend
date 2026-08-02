package com.medcare.mapper;

import com.medcare.dto.ConsultationDTO;
import com.medcare.entity.Consultation;
import com.medcare.entity.Patient;
import com.medcare.entity.User;

public class ConsultationMapper {

    public static ConsultationDTO toDTO(Consultation consultation) {
        if (consultation == null) return null;
        ConsultationDTO dto = new ConsultationDTO();
        dto.setId(consultation.getId());
        dto.setDateConsultation(consultation.getDateConsultation());
        dto.setSymptomes(consultation.getSymptomes());
        dto.setDiagnostic(consultation.getDiagnostic());
        dto.setTraitement(consultation.getTraitement());
        dto.setObservations(consultation.getObservations());

        if (consultation.getPatient() != null) {
            Patient p = consultation.getPatient();
            dto.setPatientId(p.getId());
            dto.setPatientNom(p.getNom());
            dto.setPatientPrenom(p.getPrenom());
        }

        if (consultation.getMedecin() != null) {
            User m = consultation.getMedecin();
            dto.setMedecinId(m.getId());
            dto.setMedecinNom(m.getNom());
            dto.setMedecinPrenom(m.getPrenom());
        }

        return dto;
    }

    public static Consultation toEntity(ConsultationDTO dto) {
        if (dto == null) return null;
        Consultation consultation = new Consultation();
        consultation.setId(dto.getId());
        consultation.setDateConsultation(dto.getDateConsultation());
        consultation.setSymptomes(dto.getSymptomes());
        consultation.setDiagnostic(dto.getDiagnostic());
        consultation.setTraitement(dto.getTraitement());
        consultation.setObservations(dto.getObservations());
        return consultation;
    }
}
