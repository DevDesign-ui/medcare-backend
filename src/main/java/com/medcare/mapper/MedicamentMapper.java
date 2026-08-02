package com.medcare.mapper;

import com.medcare.dto.MedicamentDTO;
import com.medcare.entity.Medicament;
import com.medcare.entity.Stock;

public class MedicamentMapper {

    public static MedicamentDTO toDTO(Medicament medicament) {
        if (medicament == null) return null;
        MedicamentDTO dto = new MedicamentDTO();
        dto.setId(medicament.getId());
        dto.setNom(medicament.getNom());
        dto.setCategorie(medicament.getCategorie());
        dto.setDescription(medicament.getDescription());
        dto.setPrix(medicament.getPrix());
        dto.setQuantite(medicament.getQuantite());
        dto.setDateExpiration(medicament.getDateExpiration());

        if (medicament.getStock() != null) {
            Stock stock = medicament.getStock();
            dto.setQuantiteDisponibleStock(stock.getQuantiteDisponible());
            dto.setSeuilAlerteStock(stock.getSeuilAlerte());
        }

        return dto;
    }

    public static Medicament toEntity(MedicamentDTO dto) {
        if (dto == null) return null;
        Medicament medicament = new Medicament();
        medicament.setId(dto.getId());
        medicament.setNom(dto.getNom());
        medicament.setCategorie(dto.getCategorie());
        medicament.setDescription(dto.getDescription());
        medicament.setPrix(dto.getPrix());
        medicament.setQuantite(dto.getQuantite());
        medicament.setDateExpiration(dto.getDateExpiration());
        return medicament;
    }
}
