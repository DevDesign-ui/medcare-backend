package com.medcare.mapper;

import com.medcare.dto.RendezVousDTO;
import com.medcare.entity.RendezVous;
import com.medcare.entity.StatutRendezVous;
import com.medcare.entity.Patient;
import com.medcare.entity.User;

public class RendezVousMapper {

    public static RendezVousDTO toDTO(RendezVous rdv) {
        if (rdv == null) return null;
        RendezVousDTO dto = new RendezVousDTO();
        dto.setId(rdv.getId());
        dto.setDate(rdv.getDate());
        dto.setHeure(rdv.getHeure());
        dto.setMotif(rdv.getMotif());
        dto.setStatut(rdv.getStatut() != null ? rdv.getStatut().name() : null);

        if (rdv.getPatient() != null) {
            Patient p = rdv.getPatient();
            dto.setPatientId(p.getId());
            dto.setPatientNom(p.getNom());
            dto.setPatientPrenom(p.getPrenom());
        }

        if (rdv.getMedecin() != null) {
            User m = rdv.getMedecin();
            dto.setMedecinId(m.getId());
            dto.setMedecinNom(m.getNom());
            dto.setMedecinPrenom(m.getPrenom());
        }

        return dto;
    }

    public static RendezVous toEntity(RendezVousDTO dto) {
        if (dto == null) return null;
        RendezVous rdv = new RendezVous();
        rdv.setId(dto.getId());
        rdv.setDate(dto.getDate());
        rdv.setHeure(dto.getHeure());
        rdv.setMotif(dto.getMotif());
        if (dto.getStatut() != null && !dto.getStatut().isEmpty()) {
            rdv.setStatut(StatutRendezVous.valueOf(dto.getStatut()));
        }
        return rdv;
    }
}
