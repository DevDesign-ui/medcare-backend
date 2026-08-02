package com.medcare.controller;

import com.medcare.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "API de statistiques du dispensaire")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtenir les statistiques generales du dispensaire", description = "Retourne le nombre de patients, consultations, rendez-vous, medicaments, factures et utilisateurs (ADMIN uniquement)")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}
