package com.medcare.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ConsultationDTO {

    private Long id;

    private LocalDateTime dateConsultation;

    private String symptomes;
    private String diagnostic;
    private String traitement;
    private String observations;

    @NotNull
    private Long patientId;

    @NotNull
    private Long medecinId;

    private String patientNom;
    private String patientPrenom;
    private String medecinNom;
    private String medecinPrenom;

    public ConsultationDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }
    public String getSymptomes() { return symptomes; }
    public void setSymptomes(String symptomes) { this.symptomes = symptomes; }
    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }
    public String getTraitement() { return traitement; }
    public void setTraitement(String traitement) { this.traitement = traitement; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }
    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public String getPatientPrenom() { return patientPrenom; }
    public void setPatientPrenom(String patientPrenom) { this.patientPrenom = patientPrenom; }
    public String getMedecinNom() { return medecinNom; }
    public void setMedecinNom(String medecinNom) { this.medecinNom = medecinNom; }
    public String getMedecinPrenom() { return medecinPrenom; }
    public void setMedecinPrenom(String medecinPrenom) { this.medecinPrenom = medecinPrenom; }
}
