package com.medcare.service.impl;

import com.medcare.dto.StockDTO;
import com.medcare.entity.Medicament;
import com.medcare.entity.Stock;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.StockMapper;
import com.medcare.repository.MedicamentRepository;
import com.medcare.repository.StockRepository;
import com.medcare.service.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final MedicamentRepository medicamentRepository;

    public StockServiceImpl(StockRepository stockRepository, MedicamentRepository medicamentRepository) {
        this.stockRepository = stockRepository;
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<StockDTO> findAll() {
        return stockRepository.findAll().stream()
                .map(StockMapper::toDTO)
                .toList();
    }

    @Override
    public StockDTO findById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock non trouve avec l'id : " + id));
        return StockMapper.toDTO(stock);
    }

    @Override
    public StockDTO update(Long id, StockDTO dto) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock non trouve avec l'id : " + id));

        stock.setQuantiteDisponible(dto.getQuantiteDisponible());
        stock.setSeuilAlerte(dto.getSeuilAlerte());
        stock.setDateMiseAJour(LocalDateTime.now());

        if (dto.getMedicamentId() != null) {
            Medicament medicament = medicamentRepository.findById(dto.getMedicamentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medicament non trouve avec l'id : " + dto.getMedicamentId()));
            stock.setMedicament(medicament);
        }

        Stock updated = stockRepository.save(stock);
        return StockMapper.toDTO(updated);
    }
}
