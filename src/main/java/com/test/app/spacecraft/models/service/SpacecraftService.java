package com.test.app.spacecraft.models.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.app.spacecraft.exception.ResourceNotFoundException;
import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import com.test.app.spacecraft.models.dto.SpacecraftRequestDTO;
import com.test.app.spacecraft.models.dto.SpacecraftResponseDTO;
import com.test.app.spacecraft.models.entity.Spacecraft;

@Service
public class SpacecraftService {
	
    private static final String SPACECRAFT_NOT_FOUND_WITH_ID = "Spacecraft not found with id ";
	
    private final ModelMapper modelMapper;
    private final SpacecraftRepository spacecraftRepository;

    public SpacecraftService(SpacecraftRepository spacecraftRepository, ModelMapper modelMapper) {
        this.spacecraftRepository = spacecraftRepository;
        this.modelMapper = modelMapper;
    }

    public Page<SpacecraftResponseDTO> findAll(Pageable pageable) {
        return spacecraftRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Cacheable("spacecraft")
    public SpacecraftResponseDTO findById(Long id) {
        return spacecraftRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(SPACECRAFT_NOT_FOUND_WITH_ID + id));
    }

    public List<SpacecraftResponseDTO> findByNameContaining(String name) {
        return spacecraftRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SpacecraftResponseDTO save(SpacecraftRequestDTO spacecraftRequestDTO) {
    	try {
            Spacecraft spacecraft = modelMapper.map(spacecraftRequestDTO, Spacecraft.class);
            Spacecraft savedSpacecraft = spacecraftRepository.save(spacecraft);
            return modelMapper.map(savedSpacecraft, SpacecraftResponseDTO.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error creating spacecraft: " + ex.getMessage(), ex);
        }
    }

    public SpacecraftResponseDTO update(Long id, SpacecraftRequestDTO spacecraftRequestDTO) {
        Optional<Spacecraft> optionalSpacecraft = spacecraftRepository.findById(id);
        if (optionalSpacecraft.isPresent()) {
            Spacecraft spacecraft = optionalSpacecraft.get();
            spacecraft.setName(spacecraftRequestDTO.getName());
            spacecraft.setSeries(spacecraftRequestDTO.getSeries());
            Spacecraft updatedSpacecraft = spacecraftRepository.save(spacecraft);
            return modelMapper.map(updatedSpacecraft, SpacecraftResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(SPACECRAFT_NOT_FOUND_WITH_ID + id);
        }
    }

    public void deleteById(Long id) {
        if (!spacecraftRepository.existsById(id)) {
            throw new ResourceNotFoundException(SPACECRAFT_NOT_FOUND_WITH_ID + id);
        }
        spacecraftRepository.deleteById(id);
    }

    private SpacecraftResponseDTO convertToDTO(Spacecraft spacecraft) {
        return modelMapper.map(spacecraft, SpacecraftResponseDTO.class);
    }

   
}

