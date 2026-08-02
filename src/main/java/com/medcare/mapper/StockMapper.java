package com.medcare.mapper;

import com.medcare.dto.StockDTO;
import com.medcare.entity.Stock;
import com.medcare.entity.Medicament;

public class StockMapper {

    public static StockDTO toDTO(Stock stock) {
        if (stock == null) return null;
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setQuantiteDisponible(stock.getQuantiteDisponible());
        dto.setSeuilAlerte(stock.getSeuilAlerte());
        dto.setDateMiseAJour(stock.getDateMiseAJour());

        if (stock.getMedicament() != null) {
            Medicament m = stock.getMedicament();
            dto.setMedicamentId(m.getId());
            dto.setMedicamentNom(m.getNom());
        }

        return dto;
    }

    public static Stock toEntity(StockDTO dto) {
        if (dto == null) return null;
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setQuantiteDisponible(dto.getQuantiteDisponible());
        stock.setSeuilAlerte(dto.getSeuilAlerte());
        stock.setDateMiseAJour(dto.getDateMiseAJour());
        return stock;
    }
}
