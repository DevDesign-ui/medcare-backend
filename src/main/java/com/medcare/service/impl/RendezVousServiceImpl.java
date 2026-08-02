package com.medcare.service.impl;

import com.medcare.dto.RendezVousDTO;
import com.medcare.entity.Patient;
import com.medcare.entity.RendezVous;
import com.medcare.entity.StatutRendezVous;
import com.medcare.entity.User;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.RendezVousMapper;
import com.medcare.repository.PatientRepository;
import com.medcare.repository.RendezVousRepository;
import com.medcare.repository.UserRepository;
import com.medcare.service.RendezVousService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository,
                                  PatientRepository patientRepository,
                                  UserRepository userRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RendezVousDTO> findAll() {
        return rendezVousRepository.findAll().stream()
                .map(RendezVousMapper::toDTO)
                .toList();
    }

    @Override
    public RendezVousDTO findById(Long id) {
        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous non trouve avec l'id : " + id));
        return RendezVousMapper.toDTO(rdv);
    }

    @Override
    public RendezVousDTO create(RendezVousDTO dto) {
        RendezVous rdv = RendezVousMapper.toEntity(dto);

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));
        User medecin = userRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Medecin non trouve avec l'id : " + dto.getMedecinId()));

        rdv.setPatient(patient);
        rdv.setMedecin(medecin);

        if (rdv.getStatut() == null) {
            rdv.setStatut(StatutRendezVous.PLANIFIE);
        }

        RendezVous saved = rendezVousRepository.save(rdv);
        return RendezVousMapper.toDTO(saved);
    }

    @Override
    public RendezVousDTO update(Long id, RendezVousDTO dto) {
        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous non trouve avec l'id : " + id));

        rdv.setDate(dto.getDate());
        rdv.setHeure(dto.getHeure());
        rdv.setMotif(dto.getMotif());

        if (dto.getStatut() != null && !dto.getStatut().isEmpty()) {
            rdv.setStatut(StatutRendezVous.valueOf(dto.getStatut()));
        }

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));
            rdv.setPatient(patient);
        }

        if (dto.getMedecinId() != null) {
            User medecin = userRepository.findById(dto.getMedecinId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medecin non trouve avec l'id : " + dto.getMedecinId()));
            rdv.setMedecin(medecin);
        }

        RendezVous updated = rendezVousRepository.save(rdv);
        return RendezVousMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rendez-vous non trouve avec l'id : " + id);
        }
        rendezVousRepository.deleteById(id);
    }
}
