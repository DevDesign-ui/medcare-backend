package com.medcare.controller;

import com.medcare.dto.FactureDTO;
import com.medcare.service.FactureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@Tag(name = "Factures", description = "API de gestion des factures")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    @Operation(summary = "Lister toutes les factures")
    public ResponseEntity<List<FactureDTO>> findAll() {
        return ResponseEntity.ok(factureService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    @Operation(summary = "Obtenir une facture par ID")
    public ResponseEntity<FactureDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    @Operation(summary = "Creer une nouvelle facture")
    public ResponseEntity<FactureDTO> create(@Valid @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SECRETAIRE')")
    @Operation(summary = "Mettre a jour une facture")
    public ResponseEntity<FactureDTO> update(@PathVariable Long id, @Valid @RequestBody FactureDTO dto) {
        return ResponseEntity.ok(factureService.update(id, dto));
    }
}
