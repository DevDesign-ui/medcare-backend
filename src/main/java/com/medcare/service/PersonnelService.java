package com.medcare.service;

import com.medcare.dto.PersonnelDTO;

import java.util.List;

public interface PersonnelService {
    List<PersonnelDTO> findAll();
    PersonnelDTO findById(Long id);
    PersonnelDTO create(PersonnelDTO dto);
    PersonnelDTO update(Long id, PersonnelDTO dto);
    void delete(Long id);
}
