package com.test.app.spacecraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.test.app.spacecraft.exception.ResourceNotFoundException;
import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import com.test.app.spacecraft.models.dto.SpacecraftRequestDTO;
import com.test.app.spacecraft.models.dto.SpacecraftResponseDTO;
import com.test.app.spacecraft.models.entity.Spacecraft;
import com.test.app.spacecraft.models.service.SpacecraftService;

@SpringBootTest
public class SpacecraftServiceTest {

    private static final Long SPACECRAFT_ID = 1L;
    private static final String SPACECRAFT_NAME = "Spacecraft 1";
    private static final String SPACECRAFT_SERIES = "Series 1";
    private static final String UPDATED_SPACECRAFT_NAME = "Updated Spacecraft";
    private static final String UPDATED_SPACECRAFT_SERIES = "Updated Series";
    private static final String NEW_SPACECRAFT_NAME = "New Spacecraft";
    private static final String NEW_SPACECRAFT_SERIES = "New Series";
    private static final String NAME_CONTAINS = "craft";

    @Mock
    private SpacecraftRepository spacecraftRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SpacecraftService spacecraftService;

    private Spacecraft spacecraft;
    private SpacecraftRequestDTO spacecraftRequestDTO;
    private SpacecraftResponseDTO spacecraftResponseDTO;
    private Spacecraft updatedSpacecraft;
    private SpacecraftRequestDTO updatedSpacecraftRequestDTO;
    private SpacecraftResponseDTO updatedSpacecraftResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        spacecraftService = new SpacecraftService(spacecraftRepository, modelMapper);

        spacecraft = new Spacecraft(SPACECRAFT_ID, SPACECRAFT_NAME, SPACECRAFT_SERIES);
        spacecraftRequestDTO = new SpacecraftRequestDTO(NEW_SPACECRAFT_NAME, NEW_SPACECRAFT_SERIES);
        spacecraftResponseDTO = new SpacecraftResponseDTO(SPACECRAFT_ID, NEW_SPACECRAFT_NAME, NEW_SPACECRAFT_SERIES);
        updatedSpacecraft = new Spacecraft(SPACECRAFT_ID, UPDATED_SPACECRAFT_NAME, UPDATED_SPACECRAFT_SERIES);
        updatedSpacecraftRequestDTO = new SpacecraftRequestDTO(UPDATED_SPACECRAFT_NAME, UPDATED_SPACECRAFT_SERIES);
        updatedSpacecraftResponseDTO = new SpacecraftResponseDTO(SPACECRAFT_ID, UPDATED_SPACECRAFT_NAME, UPDATED_SPACECRAFT_SERIES);
    }

    @Test
    public void testFindById() {
        when(spacecraftRepository.findById(SPACECRAFT_ID)).thenReturn(Optional.of(spacecraft));
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftResponseDTO.class)))
                .thenReturn(new SpacecraftResponseDTO(spacecraft.getId(), spacecraft.getName(), spacecraft.getSeries()));

        SpacecraftResponseDTO result = spacecraftService.findById(SPACECRAFT_ID);
        assertEquals(SPACECRAFT_NAME, result.getName());
        assertEquals(SPACECRAFT_SERIES, result.getSeries());
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(spacecraftRepository.findById(SPACECRAFT_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.findById(SPACECRAFT_ID));
    }

    @Test
    public void testFindByNameContaining() {
        List<Spacecraft> spacecraftList = Arrays.asList(spacecraft, updatedSpacecraft);
        when(spacecraftRepository.findByNameContaining(NAME_CONTAINS)).thenReturn(spacecraftList);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Spacecraft s = invocation.getArgument(0);
                    return new SpacecraftResponseDTO(s.getId(), s.getName(), s.getSeries());
                });

        List<SpacecraftResponseDTO> result = spacecraftService.findByNameContaining(NAME_CONTAINS);
        assertEquals(2, result.size());
        assertEquals(SPACECRAFT_NAME, result.get(0).getName());
    }

    @Test
    public void testSave() {
        when(modelMapper.map(any(SpacecraftRequestDTO.class), eq(Spacecraft.class))).thenReturn(spacecraft);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftResponseDTO.class))).thenReturn(spacecraftResponseDTO);
        when(spacecraftRepository.save(spacecraft)).thenReturn(spacecraft);

        SpacecraftResponseDTO result = spacecraftService.save(spacecraftRequestDTO);
        assertNotNull(result.getId());
        assertEquals(NEW_SPACECRAFT_NAME, result.getName());
        assertEquals(NEW_SPACECRAFT_SERIES, result.getSeries());
    }

    @Test
    public void testUpdate() {
        when(spacecraftRepository.findById(SPACECRAFT_ID)).thenReturn(Optional.of(spacecraft));
        when(modelMapper.map(any(SpacecraftResponseDTO.class), eq(Spacecraft.class))).thenReturn(updatedSpacecraft);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftResponseDTO.class))).thenReturn(updatedSpacecraftResponseDTO);
        when(spacecraftRepository.save(updatedSpacecraft)).thenReturn(updatedSpacecraft);

        SpacecraftResponseDTO result = spacecraftService.update(SPACECRAFT_ID, updatedSpacecraftRequestDTO);
        assertEquals(SPACECRAFT_ID, result.getId());
        assertEquals(UPDATED_SPACECRAFT_NAME, result.getName());
        assertEquals(UPDATED_SPACECRAFT_SERIES, result.getSeries());
    }

    @Test
    public void testDeleteById() {
        when(spacecraftRepository.existsById(SPACECRAFT_ID)).thenReturn(true);
        spacecraftService.deleteById(SPACECRAFT_ID);
        verify(spacecraftRepository).deleteById(SPACECRAFT_ID);
    }

    @Test
    public void testFindAll() {
        Spacecraft spacecraft1 = new Spacecraft(1L, "X-Wing", "Star Wars");
        Spacecraft spacecraft2 = new Spacecraft(2L, "TIE Fighter", "Star Wars");
        List<Spacecraft> spacecraftList = Arrays.asList(spacecraft1, spacecraft2);
        Page<Spacecraft> page = new PageImpl<>(spacecraftList);

        Pageable pageable = PageRequest.of(0, 10);
        when(spacecraftRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftResponseDTO.class)))
                .thenAnswer(invocation -> {
                    Spacecraft s = invocation.getArgument(0);
                    return new SpacecraftResponseDTO(s.getId(), s.getName(), s.getSeries());
                });

        Page<SpacecraftResponseDTO> result = spacecraftService.findAll(pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals(spacecraft1.getName(), result.getContent().get(0).getName());
        assertEquals(spacecraft2.getName(), result.getContent().get(1).getName());
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(spacecraftRepository.existsById(SPACECRAFT_ID)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.deleteById(SPACECRAFT_ID));
    }
}