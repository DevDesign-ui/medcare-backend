package com.medcare.service;

import com.medcare.dto.RendezVousDTO;

import java.util.List;

public interface RendezVousService {
    List<RendezVousDTO> findAll();
    RendezVousDTO findById(Long id);
    RendezVousDTO create(RendezVousDTO dto);
    RendezVousDTO update(Long id, RendezVousDTO dto);
    void delete(Long id);
}
