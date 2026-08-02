package com.medcare.controller;

import com.medcare.dto.PersonnelDTO;
import com.medcare.service.PersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnels")
@Tag(name = "Personnel", description = "API de gestion du personnel du dispensaire")
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lister tout le personnel")
    public ResponseEntity<List<PersonnelDTO>> findAll() {
        return ResponseEntity.ok(personnelService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir un personnel par ID")
    public ResponseEntity<PersonnelDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(personnelService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creer un nouveau personnel")
    public ResponseEntity<PersonnelDTO> create(@Valid @RequestBody PersonnelDTO dto) {
        return ResponseEntity.ok(personnelService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre a jour un personnel")
    public ResponseEntity<PersonnelDTO> update(@PathVariable Long id, @Valid @RequestBody PersonnelDTO dto) {
        return ResponseEntity.ok(personnelService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un personnel")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personnelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
