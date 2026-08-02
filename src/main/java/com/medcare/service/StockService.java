package com.medcare.service;

import com.medcare.dto.StockDTO;

import java.util.List;

public interface StockService {
    List<StockDTO> findAll();
    StockDTO findById(Long id);
    StockDTO update(Long id, StockDTO dto);
}
