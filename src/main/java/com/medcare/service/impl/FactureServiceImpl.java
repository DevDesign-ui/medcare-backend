package com.medcare.service.impl;

import com.medcare.dto.FactureDTO;
import com.medcare.entity.Facture;
import com.medcare.entity.Patient;
import com.medcare.entity.StatutPaiement;
import com.medcare.exception.ResourceAlreadyExistsException;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.FactureMapper;
import com.medcare.repository.FactureRepository;
import com.medcare.repository.PatientRepository;
import com.medcare.service.FactureService;
import com.medcare.util.InvoiceNumberGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final PatientRepository patientRepository;
    private final InvoiceNumberGenerator invoiceNumberGenerator;

    public FactureServiceImpl(FactureRepository factureRepository,
                               PatientRepository patientRepository,
                               InvoiceNumberGenerator invoiceNumberGenerator) {
        this.factureRepository = factureRepository;
        this.patientRepository = patientRepository;
        this.invoiceNumberGenerator = invoiceNumberGenerator;
    }

    @Override
    public List<FactureDTO> findAll() {
        return factureRepository.findAll().stream()
                .map(FactureMapper::toDTO)
                .toList();
    }

    @Override
    public FactureDTO findById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvee avec l'id : " + id));
        return FactureMapper.toDTO(facture);
    }

    @Override
    public FactureDTO create(FactureDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));

        Facture facture = new Facture();
        facture.setMontant(dto.getMontant());
        facture.setPatient(patient);

        if (dto.getNumeroFacture() != null && !dto.getNumeroFacture().isEmpty()) {
            if (factureRepository.existsByNumeroFacture(dto.getNumeroFacture())) {
                throw new ResourceAlreadyExistsException("Une facture avec ce numero existe deja");
            }
            facture.setNumeroFacture(dto.getNumeroFacture());
        } else {
            facture.setNumeroFacture(invoiceNumberGenerator.generate());
        }

        if (dto.getDateFacture() != null) {
            facture.setDateFacture(dto.getDateFacture());
        }

        if (dto.getStatutPaiement() != null && !dto.getStatutPaiement().isEmpty()) {
            facture.setStatutPaiement(StatutPaiement.valueOf(dto.getStatutPaiement()));
        } else {
            facture.setStatutPaiement(StatutPaiement.IMPAYEE);
        }

        Facture saved = factureRepository.save(facture);
        return FactureMapper.toDTO(saved);
    }

    @Override
    public FactureDTO update(Long id, FactureDTO dto) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvee avec l'id : " + id));

        facture.setMontant(dto.getMontant());

        if (dto.getDateFacture() != null) {
            facture.setDateFacture(dto.getDateFacture());
        }

        if (dto.getStatutPaiement() != null && !dto.getStatutPaiement().isEmpty()) {
            facture.setStatutPaiement(StatutPaiement.valueOf(dto.getStatutPaiement()));
        }

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));
            facture.setPatient(patient);
        }

        Facture updated = factureRepository.save(facture);
        return FactureMapper.toDTO(updated);
    }
}
