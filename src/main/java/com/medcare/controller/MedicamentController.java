package com.medcare.controller;

import com.medcare.dto.MedicamentDTO;
import com.medcare.service.MedicamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicaments")
@Tag(name = "Medicaments", description = "API de gestion des medicaments")
public class MedicamentController {

    private final MedicamentService medicamentService;

    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Lister tous les medicaments")
    public ResponseEntity<List<MedicamentDTO>> findAll() {
        return ResponseEntity.ok(medicamentService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Obtenir un medicament par ID")
    public ResponseEntity<MedicamentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Creer un nouveau medicament")
    public ResponseEntity<MedicamentDTO> create(@Valid @RequestBody MedicamentDTO dto) {
        return ResponseEntity.ok(medicamentService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIEN')")
    @Operation(summary = "Mettre a jour un medicament")
    public ResponseEntity<MedicamentDTO> update(@PathVariable Long id, @Valid @RequestBody MedicamentDTO dto) {
        return ResponseEntity.ok(medicamentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un medicament")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicamentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
