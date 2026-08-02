package com.medcare.controller;

import com.medcare.dto.RendezVousDTO;
import com.medcare.service.RendezVousService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@Tag(name = "Rendez-vous", description = "API de gestion des rendez-vous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','SECRETAIRE')")
    @Operation(summary = "Lister tous les rendez-vous")
    public ResponseEntity<List<RendezVousDTO>> findAll() {
        return ResponseEntity.ok(rendezVousService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','SECRETAIRE')")
    @Operation(summary = "Obtenir un rendez-vous par ID")
    public ResponseEntity<RendezVousDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','SECRETAIRE')")
    @Operation(summary = "Creer un nouveau rendez-vous")
    public ResponseEntity<RendezVousDTO> create(@Valid @RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rendezVousService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','SECRETAIRE')")
    @Operation(summary = "Mettre a jour un rendez-vous")
    public ResponseEntity<RendezVousDTO> update(@PathVariable Long id, @Valid @RequestBody RendezVousDTO dto) {
        return ResponseEntity.ok(rendezVousService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','SECRETAIRE')")
    @Operation(summary = "Supprimer un rendez-vous")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rendezVousService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
