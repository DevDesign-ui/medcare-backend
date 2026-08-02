package com.medcare.service;

import com.medcare.dto.ConsultationDTO;

import java.util.List;

public interface ConsultationService {
    List<ConsultationDTO> findAll();
    ConsultationDTO findById(Long id);
    ConsultationDTO create(ConsultationDTO dto);
    ConsultationDTO update(Long id, ConsultationDTO dto);
    void delete(Long id);
}
