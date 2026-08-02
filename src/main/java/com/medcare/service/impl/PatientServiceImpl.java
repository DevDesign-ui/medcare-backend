package com.medcare.service.impl;

import com.medcare.dto.PatientDTO;
import com.medcare.entity.Patient;
import com.medcare.exception.ResourceAlreadyExistsException;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.PatientMapper;
import com.medcare.repository.PatientRepository;
import com.medcare.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    @Override
    public PatientDTO findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + id));
        return PatientMapper.toDTO(patient);
    }

    @Override
    public PatientDTO create(PatientDTO dto) {
        if (dto.getEmail() != null && !dto.getEmail().isEmpty() && patientRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException("Un patient avec cet email existe deja");
        }
        Patient patient = PatientMapper.toEntity(dto);
        Patient saved = patientRepository.save(patient);
        return PatientMapper.toDTO(saved);
    }

    @Override
    public PatientDTO update(Long id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient non trouve avec l'id : " + id));

        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setDateNaissance(dto.getDateNaissance());
        if (dto.getSexe() != null && !dto.getSexe().isEmpty()) {
            patient.setSexe(com.medcare.entity.Sexe.valueOf(dto.getSexe()));
        }
        patient.setTelephone(dto.getTelephone());
        patient.setAdresse(dto.getAdresse());
        patient.setGroupeSanguin(dto.getGroupeSanguin());
        patient.setEmail(dto.getEmail());

        Patient updated = patientRepository.save(patient);
        return PatientMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient non trouve avec l'id : " + id);
        }
        patientRepository.deleteById(id);
    }
}
