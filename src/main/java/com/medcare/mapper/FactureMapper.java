package com.medcare.mapper;

import com.medcare.dto.FactureDTO;
import com.medcare.entity.Facture;
import com.medcare.entity.StatutPaiement;
import com.medcare.entity.Patient;

public class FactureMapper {

    public static FactureDTO toDTO(Facture facture) {
        if (facture == null) return null;
        FactureDTO dto = new FactureDTO();
        dto.setId(facture.getId());
        dto.setNumeroFacture(facture.getNumeroFacture());
        dto.setMontant(facture.getMontant());
        dto.setDateFacture(facture.getDateFacture());
        dto.setStatutPaiement(facture.getStatutPaiement() != null ? facture.getStatutPaiement().name() : null);

        if (facture.getPatient() != null) {
            Patient p = facture.getPatient();
            dto.setPatientId(p.getId());
            dto.setPatientNom(p.getNom());
            dto.setPatientPrenom(p.getPrenom());
        }

        return dto;
    }

    public static Facture toEntity(FactureDTO dto) {
        if (dto == null) return null;
        Facture facture = new Facture();
        facture.setId(dto.getId());
        facture.setNumeroFacture(dto.getNumeroFacture());
        facture.setMontant(dto.getMontant());
        facture.setDateFacture(dto.getDateFacture());
        if (dto.getStatutPaiement() != null && !dto.getStatutPaiement().isEmpty()) {
            facture.setStatutPaiement(StatutPaiement.valueOf(dto.getStatutPaiement()));
        }
        return facture;
    }
}
