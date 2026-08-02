package com.medcare.controller;

import com.medcare.dto.ConsultationDTO;
import com.medcare.service.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@Tag(name = "Consultations", description = "API de gestion des consultations medicales")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @Operation(summary = "Lister toutes les consultations")
    public ResponseEntity<List<ConsultationDTO>> findAll() {
        return ResponseEntity.ok(consultationService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @Operation(summary = "Obtenir une consultation par ID")
    public ResponseEntity<ConsultationDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @Operation(summary = "Creer une nouvelle consultation")
    public ResponseEntity<ConsultationDTO> create(@Valid @RequestBody ConsultationDTO dto) {
        return ResponseEntity.ok(consultationService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @Operation(summary = "Mettre a jour une consultation")
    public ResponseEntity<ConsultationDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultationDTO dto) {
        return ResponseEntity.ok(consultationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une consultation")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
