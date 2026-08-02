package com.medcare.service.impl;

import com.medcare.dto.MedicamentDTO;
import com.medcare.entity.Medicament;
import com.medcare.entity.Stock;
import com.medcare.exception.ResourceAlreadyExistsException;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.MedicamentMapper;
import com.medcare.repository.MedicamentRepository;
import com.medcare.repository.StockRepository;
import com.medcare.service.MedicamentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentRepository medicamentRepository;
    private final StockRepository stockRepository;

    public MedicamentServiceImpl(MedicamentRepository medicamentRepository, StockRepository stockRepository) {
        this.medicamentRepository = medicamentRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public List<MedicamentDTO> findAll() {
        return medicamentRepository.findAll().stream()
                .map(MedicamentMapper::toDTO)
                .toList();
    }

    @Override
    public MedicamentDTO findById(Long id) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicament non trouve avec l'id : " + id));
        return MedicamentMapper.toDTO(medicament);
    }

    @Override
    @Transactional
    public MedicamentDTO create(MedicamentDTO dto) {
        if (medicamentRepository.existsByNom(dto.getNom())) {
            throw new ResourceAlreadyExistsException("Un medicament avec ce nom existe deja");
        }

        Medicament medicament = MedicamentMapper.toEntity(dto);
        Medicament saved = medicamentRepository.save(medicament);

        Stock stock = new Stock();
        stock.setMedicament(saved);
        stock.setQuantiteDisponible(dto.getQuantiteDisponibleStock() != null ? dto.getQuantiteDisponibleStock() : 0);
        stock.setSeuilAlerte(dto.getSeuilAlerteStock() != null ? dto.getSeuilAlerteStock() : 10);
        stock.setDateMiseAJour(java.time.LocalDateTime.now());
        stockRepository.save(stock);

        saved.setStock(stock);
        return MedicamentMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public MedicamentDTO update(Long id, MedicamentDTO dto) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicament non trouve avec l'id : " + id));

        medicament.setNom(dto.getNom());
        medicament.setCategorie(dto.getCategorie());
        medicament.setDescription(dto.getDescription());
        medicament.setPrix(dto.getPrix());
        medicament.setQuantite(dto.getQuantite());
        medicament.setDateExpiration(dto.getDateExpiration());

        if (dto.getQuantiteDisponibleStock() != null || dto.getSeuilAlerteStock() != null) {
            Stock stock = stockRepository.findByMedicamentId(id).orElse(null);
            if (stock == null) {
                stock = new Stock();
                stock.setMedicament(medicament);
            }
            if (dto.getQuantiteDisponibleStock() != null) {
                stock.setQuantiteDisponible(dto.getQuantiteDisponibleStock());
            }
            if (dto.getSeuilAlerteStock() != null) {
                stock.setSeuilAlerte(dto.getSeuilAlerteStock());
            }
            stock.setDateMiseAJour(java.time.LocalDateTime.now());
            stockRepository.save(stock);
            medicament.setStock(stock);
        }

        Medicament updated = medicamentRepository.save(medicament);
        return MedicamentMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicament non trouve avec l'id : " + id));
        stockRepository.findByMedicamentId(id).ifPresent(stockRepository::delete);
        medicamentRepository.delete(medicament);
    }
}
