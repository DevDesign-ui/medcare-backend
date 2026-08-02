package com.medcare.service;

import com.medcare.dto.MedicamentDTO;

import java.util.List;

public interface MedicamentService {
    List<MedicamentDTO> findAll();
    MedicamentDTO findById(Long id);
    MedicamentDTO create(MedicamentDTO dto);
    MedicamentDTO update(Long id, MedicamentDTO dto);
    void delete(Long id);
}
