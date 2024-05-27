package com.test.app.spacecraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        Mockito.when(spacecraftRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> spacecraftService.findById(1L));
    }
}
