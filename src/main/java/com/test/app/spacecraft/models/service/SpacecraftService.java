package com.test.app.spacecraft.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.app.spacecraft.exception.ResourceNotFoundException;
import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import com.test.app.spacecraft.models.entity.Spacecraft;

@Service
public class SpacecraftService {

    @Autowired
    private SpacecraftRepository spacecraftRepository;

    public Page<Spacecraft> findAll(Pageable pageable) {
        return spacecraftRepository.findAll(pageable);
    }

    @Cacheable("spacecraft")
    public Spacecraft findById(Long id) {
        return spacecraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spacecraft not found with id " + id));
    }

    public List<Spacecraft> findByNameContaining(String name) {
        return spacecraftRepository.findByNameContaining(name);
    }

    public Spacecraft save(Spacecraft spacecraft) {
        return spacecraftRepository.save(spacecraft);
    }

    public Spacecraft update(Spacecraft spacecraft) {
        if (!spacecraftRepository.existsById(spacecraft.getId())) {
            throw new ResourceNotFoundException("Spacecraft not found with id " + spacecraft.getId());
        }
        return spacecraftRepository.save(spacecraft);
    }

    public void deleteById(Long id) {
        if (!spacecraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Spacecraft not found with id " + id);
        }
        spacecraftRepository.deleteById(id);
    }
}
