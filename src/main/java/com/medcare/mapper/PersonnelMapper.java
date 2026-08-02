package com.medcare.mapper;

import com.medcare.dto.PersonnelDTO;
import com.medcare.entity.Personnel;

public class PersonnelMapper {

    public static PersonnelDTO toDTO(Personnel personnel) {
        if (personnel == null) return null;
        PersonnelDTO dto = new PersonnelDTO();
        dto.setId(personnel.getId());
        dto.setNom(personnel.getNom());
        dto.setPrenom(personnel.getPrenom());
        dto.setFonction(personnel.getFonction());
        dto.setTelephone(personnel.getTelephone());
        dto.setEmail(personnel.getEmail());
        return dto;
    }

    public static Personnel toEntity(PersonnelDTO dto) {
        if (dto == null) return null;
        Personnel personnel = new Personnel();
        personnel.setId(dto.getId());
        personnel.setNom(dto.getNom());
        personnel.setPrenom(dto.getPrenom());
        personnel.setFonction(dto.getFonction());
        personnel.setTelephone(dto.getTelephone());
        personnel.setEmail(dto.getEmail());
        return personnel;
    }
}
