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
import com.test.app.spacecraft.models.dto.SpacecraftDTO;
import com.test.app.spacecraft.models.entity.Spacecraft;
import com.test.app.spacecraft.models.service.SpacecraftService;

@SpringBootTest
public class SpacecraftServiceTest {

	@Mock
    private SpacecraftRepository spacecraftRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SpacecraftService spacecraftService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        spacecraftService = new SpacecraftService(spacecraftRepository, modelMapper);
    }

    @Test
    public void testFindById() {
        Spacecraft spacecraft = new Spacecraft("Spacecraft 1", "Series 1");
        when(spacecraftRepository.findById(1L)).thenReturn(Optional.of(spacecraft));
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftDTO.class))).thenAnswer(invocation -> {
            Spacecraft space = invocation.getArgument(0);
            return new SpacecraftDTO(space.getId(), space.getName(), space.getSeries());
        });

        SpacecraftDTO result = spacecraftService.findById(1L);
        assertEquals("Spacecraft 1", result.getName());
        assertEquals("Series 1", result.getSeries());
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(spacecraftRepository.findById(31L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.findById(31L));
    }
    
    @Test
    public void testFindByNameContaining() {
        List<Spacecraft> spacecraftList = new ArrayList<>();
        spacecraftList.add(new Spacecraft("Spacecraft 1", "Series 1"));
        spacecraftList.add(new Spacecraft("Spacecraft 2", "Series 2"));

        when(spacecraftRepository.findByNameContaining("craft")).thenReturn(spacecraftList);

        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftDTO.class))).thenAnswer(invocation -> {
            Spacecraft s = invocation.getArgument(0);
            return new SpacecraftDTO(s.getName(), s.getSeries());
        });

        List<SpacecraftDTO> result = spacecraftService.findByNameContaining("craft");

        assertEquals(2, result.size());
        assertEquals("Spacecraft 1", result.get(0).getName());
    }

    @Test
    public void testSave() {
        SpacecraftDTO spacecraftDTO = new SpacecraftDTO(1L, "New Spacecraft", "New Series");
        Spacecraft spacecraft = new Spacecraft("New Spacecraft", "New Series");

        when(modelMapper.map(any(SpacecraftDTO.class), eq(Spacecraft.class))).thenReturn(spacecraft);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftDTO.class))).thenReturn(spacecraftDTO);
        when(spacecraftRepository.save(spacecraft)).thenReturn(spacecraft);

        SpacecraftDTO result = spacecraftService.save(spacecraftDTO);

        assertNotNull(result.getId());
        assertEquals("New Spacecraft", result.getName());
        assertEquals("New Series", result.getSeries());
    }

    @Test
    public void testUpdate() {
        SpacecraftDTO spacecraftDTO = new SpacecraftDTO(1L, "Updated Spacecraft", "Updated Series");
        Spacecraft spacecraft = new Spacecraft("Updated Spacecraft", "Updated Series");

        when(modelMapper.map(any(SpacecraftDTO.class), eq(Spacecraft.class))).thenReturn(spacecraft);
        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftDTO.class))).thenReturn(spacecraftDTO);
        when(spacecraftRepository.existsById(1L)).thenReturn(true);
        when(spacecraftRepository.save(spacecraft)).thenReturn(spacecraft);

        SpacecraftDTO result = spacecraftService.update(spacecraftDTO);

        assertEquals(1L, result.getId());
        assertEquals("Updated Spacecraft", result.getName());
        assertEquals("Updated Series", result.getSeries());
    }

    @Test
    public void testDeleteById() {
        when(spacecraftRepository.existsById(1L)).thenReturn(true);
        spacecraftService.deleteById(1L);
        verify(spacecraftRepository).deleteById(1L);
    }
    
    @Test
    public void testFindAll() {
        Spacecraft spacecraft1 = new Spacecraft("X-Wing", "Star Wars");
        Spacecraft spacecraft2 = new Spacecraft("TIE Fighter", "Star Wars");
        List<Spacecraft> spacecraftList = Arrays.asList(spacecraft1, spacecraft2);
        Page<Spacecraft> page = new PageImpl<>(spacecraftList);

        Pageable pageable = PageRequest.of(0, 10);
        when(spacecraftRepository.findAll(pageable)).thenReturn(page);

        when(modelMapper.map(any(Spacecraft.class), eq(SpacecraftDTO.class)))
            .thenAnswer(invocation -> {
                Spacecraft s = invocation.getArgument(0);
                return new SpacecraftDTO(s.getName(), s.getSeries());
            });

        Page<SpacecraftDTO> result = spacecraftService.findAll(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(spacecraft1.getName(), result.getContent().get(0).getName());
        assertEquals(spacecraft2.getName(), result.getContent().get(1).getName());
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(spacecraftRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.deleteById(1L));
    }
    
}
