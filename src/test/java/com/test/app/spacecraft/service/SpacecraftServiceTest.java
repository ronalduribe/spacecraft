package com.test.app.spacecraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.test.app.spacecraft.exception.ResourceNotFoundException;
import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import com.test.app.spacecraft.models.entity.Spacecraft;
import com.test.app.spacecraft.models.service.SpacecraftService;

@SpringBootTest
public class SpacecraftServiceTest {

    @Autowired
    private SpacecraftService spacecraftService;
    
    @MockBean
    private SpacecraftRepository spacecraftRepository;

   
    @Test
    public void testFindById() {
        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setId(1L);
        spacecraft.setName("X-Wing");
        spacecraft.setSeries("Star Wars");

        Mockito.when(spacecraftRepository.findById(1L)).thenReturn(Optional.of(spacecraft));

        Spacecraft found = spacecraftService.findById(1L);
        assertEquals("X-Wing", found.getName());
    }

    @Test
    public void testFindByIdNotFound() {
        Mockito.when(spacecraftRepository.findById(31L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.findById(31L));
    }
    
    @Test
    public void testFindAll() {
        Spacecraft spacecraft1 = new Spacecraft("X-Wing", "Star Wars");
        Spacecraft spacecraft2 = new Spacecraft("TIE Fighter", "Star Wars");
        List<Spacecraft> spacecraftList = Arrays.asList(spacecraft1, spacecraft2);
        Page<Spacecraft> page = new PageImpl<>(spacecraftList);

        Pageable pageable = PageRequest.of(0, 10);
        when(spacecraftRepository.findAll(pageable)).thenReturn(page);

        Page<Spacecraft> result = spacecraftService.findAll(pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals(spacecraftList, result.getContent());
    }


    @Test
    public void testSave() {
        Spacecraft spacecraft = new Spacecraft("X-Wing", "Star Wars");
        when(spacecraftRepository.save(spacecraft)).thenReturn(spacecraft);
        Spacecraft saved = spacecraftService.save(spacecraft);
        assertEquals("X-Wing", saved.getName());
        assertEquals("Star Wars", saved.getSeries());
    }


    @Test
    public void testUpdateNotFound() {
        Spacecraft spacecraft = new Spacecraft("X-Wing", "Star Wars");
        when(spacecraftRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.update(spacecraft));
    }

    @Test
    public void testDeleteById() {
        when(spacecraftRepository.existsById(1L)).thenReturn(true);
        spacecraftService.deleteById(1L);
        verify(spacecraftRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(spacecraftRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.deleteById(1L));
    }
    
}
