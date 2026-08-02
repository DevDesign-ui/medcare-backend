package com.medcare.service.impl;

import com.medcare.dto.ConsultationDTO;
import com.medcare.entity.Consultation;
import com.medcare.entity.Patient;
import com.medcare.entity.User;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.ConsultationMapper;
import com.medcare.repository.ConsultationRepository;
import com.medcare.repository.PatientRepository;
import com.medcare.repository.UserRepository;
import com.medcare.service.ConsultationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository,
                                    PatientRepository patientRepository,
                                    UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(ConsultationMapper::toDTO)
                .toList();
    }

    @Override
    public ConsultationDTO findById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvee avec l'id : " + id));
        return ConsultationMapper.toDTO(consultation);
    }

    @Override
    public ConsultationDTO create(ConsultationDTO dto) {
        Consultation consultation = ConsultationMapper.toEntity(dto);

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));
        User medecin = userRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new ResourceNotFoundException("Medecin non trouve avec l'id : " + dto.getMedecinId()));

        consultation.setPatient(patient);
        consultation.setMedecin(medecin);

        if (consultation.getDateConsultation() == null) {
            consultation.setDateConsultation(java.time.LocalDateTime.now());
        }

        Consultation saved = consultationRepository.save(consultation);
        return ConsultationMapper.toDTO(saved);
    }

    @Override
    public ConsultationDTO update(Long id, ConsultationDTO dto) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation non trouvee avec l'id : " + id));

        consultation.setSymptomes(dto.getSymptomes());
        consultation.setDiagnostic(dto.getDiagnostic());
        consultation.setTraitement(dto.getTraitement());
        consultation.setObservations(dto.getObservations());

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + dto.getPatientId()));
            consultation.setPatient(patient);
        }

        if (dto.getMedecinId() != null) {
            User medecin = userRepository.findById(dto.getMedecinId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medecin non trouve avec l'id : " + dto.getMedecinId()));
            consultation.setMedecin(medecin);
        }

        Consultation updated = consultationRepository.save(consultation);
        return ConsultationMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Consultation non trouvee avec l'id : " + id);
        }
        consultationRepository.deleteById(id);
    }
}
