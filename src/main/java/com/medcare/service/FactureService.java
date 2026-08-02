package com.medcare.service;

import com.medcare.dto.FactureDTO;

import java.util.List;

public interface FactureService {
    List<FactureDTO> findAll();
    FactureDTO findById(Long id);
    FactureDTO create(FactureDTO dto);
    FactureDTO update(Long id, FactureDTO dto);
}
