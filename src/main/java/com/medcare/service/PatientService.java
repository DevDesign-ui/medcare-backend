package com.medcare.service;

import com.medcare.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    List<PatientDTO> findAll();
    PatientDTO findById(Long id);
    PatientDTO create(PatientDTO dto);
    PatientDTO update(Long id, PatientDTO dto);
    void delete(Long id);
}
