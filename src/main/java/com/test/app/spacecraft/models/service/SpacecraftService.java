package com.test.app.spacecraft.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.app.spacecraft.exception.ResourceNotFoundException;
import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import com.test.app.spacecraft.models.dto.SpacecraftDTO;
import com.test.app.spacecraft.models.entity.Spacecraft;

@Service
public class SpacecraftService {
	
    private final ModelMapper modelMapper;

    private final SpacecraftRepository spacecraftRepository;

    public SpacecraftService(SpacecraftRepository spacecraftRepository, ModelMapper modelMapper) {
        this.spacecraftRepository = spacecraftRepository;
        this.modelMapper = modelMapper;
    }

    public Page<SpacecraftDTO> findAll(Pageable pageable) {
        return spacecraftRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Cacheable("spacecraft")
    public SpacecraftDTO findById(Long id) {
        return spacecraftRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Spacecraft not found with id " + id));
    }

    public List<SpacecraftDTO> findByNameContaining(String name) {
        return spacecraftRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SpacecraftDTO save(SpacecraftDTO spacecraftDTO) {
        Spacecraft spacecraft = convertToEntity(spacecraftDTO);
        return convertToDTO(spacecraftRepository.save(spacecraft));
    }

    public SpacecraftDTO update(SpacecraftDTO spacecraftDTO) {
        if (!spacecraftRepository.existsById(spacecraftDTO.getId())) {
            throw new ResourceNotFoundException("Spacecraft not found with id " + spacecraftDTO.getId());
        }
        
        Spacecraft spacecraft = convertToEntity(spacecraftDTO);
        return convertToDTO(spacecraftRepository.save(spacecraft));
    }

    public void deleteById(Long id) {
        if (!spacecraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Spacecraft not found with id " + id);
        }
        spacecraftRepository.deleteById(id);
    }

    private SpacecraftDTO convertToDTO(Spacecraft spacecraft) {
        return modelMapper.map(spacecraft, SpacecraftDTO.class);
    }

    private Spacecraft convertToEntity(SpacecraftDTO spacecraftDTO) {
    	return modelMapper.map(spacecraftDTO, Spacecraft.class);
    }
}

