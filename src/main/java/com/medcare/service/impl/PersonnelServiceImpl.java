package com.medcare.service.impl;

import com.medcare.dto.PersonnelDTO;
import com.medcare.entity.Personnel;
import com.medcare.exception.ResourceAlreadyExistsException;
import com.medcare.exception.ResourceNotFoundException;
import com.medcare.mapper.PersonnelMapper;
import com.medcare.repository.PersonnelRepository;
import com.medcare.service.PersonnelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelServiceImpl implements PersonnelService {

    private final PersonnelRepository personnelRepository;

    public PersonnelServiceImpl(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public List<PersonnelDTO> findAll() {
        return personnelRepository.findAll().stream()
                .map(PersonnelMapper::toDTO)
                .toList();
    }

    @Override
    public PersonnelDTO findById(Long id) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personnel non trouve avec l'id : " + id));
        return PersonnelMapper.toDTO(personnel);
    }

    @Override
    public PersonnelDTO create(PersonnelDTO dto) {
        if (dto.getEmail() != null && !dto.getEmail().isEmpty() && personnelRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException("Un personnel avec cet email existe deja");
        }
        Personnel personnel = PersonnelMapper.toEntity(dto);
        Personnel saved = personnelRepository.save(personnel);
        return PersonnelMapper.toDTO(saved);
    }

    @Override
    public PersonnelDTO update(Long id, PersonnelDTO dto) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personnel non trouve avec l'id : " + id));

        personnel.setNom(dto.getNom());
        personnel.setPrenom(dto.getPrenom());
        personnel.setFonction(dto.getFonction());
        personnel.setTelephone(dto.getTelephone());
        personnel.setEmail(dto.getEmail());

        Personnel updated = personnelRepository.save(personnel);
        return PersonnelMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!personnelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Personnel non trouve avec l'id : " + id);
        }
        personnelRepository.deleteById(id);
    }
}
