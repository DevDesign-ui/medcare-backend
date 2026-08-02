package com.medcare.controller;

import com.medcare.dto.StockDTO;
import com.medcare.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stocks", description = "API de gestion du stock pharmaceutique")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Lister tous les stocks")
    public ResponseEntity<List<StockDTO>> findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Obtenir un stock par ID")
    public ResponseEntity<StockDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Mettre a jour un stock")
    public ResponseEntity<StockDTO> update(@PathVariable Long id, @Valid @RequestBody StockDTO dto) {
        return ResponseEntity.ok(stockService.update(id, dto));
    }
}
