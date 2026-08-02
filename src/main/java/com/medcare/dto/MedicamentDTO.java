package com.medcare.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicamentDTO {

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nom;

    @Size(max = 100)
    private String categorie;

    private String description;

    @DecimalMin("0.0")
    private BigDecimal prix;

    @NotNull
    private Integer quantite;

    private LocalDate dateExpiration;

    private Integer quantiteDisponibleStock;
    private Integer seuilAlerteStock;

    public MedicamentDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }
    public Integer getQuantiteDisponibleStock() { return quantiteDisponibleStock; }
    public void setQuantiteDisponibleStock(Integer quantiteDisponibleStock) { this.quantiteDisponibleStock = quantiteDisponibleStock; }
    public Integer getSeuilAlerteStock() { return seuilAlerteStock; }
    public void setSeuilAlerteStock(Integer seuilAlerteStock) { this.seuilAlerteStock = seuilAlerteStock; }
}
