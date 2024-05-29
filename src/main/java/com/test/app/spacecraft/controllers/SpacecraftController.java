package com.test.app.spacecraft.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.spacecraft.models.dto.SpacecraftRequestDTO;
import com.test.app.spacecraft.models.dto.SpacecraftResponseDTO;
import com.test.app.spacecraft.models.service.SpacecraftService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/spacecrafts")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;
    

    public SpacecraftController(SpacecraftService spacecraftService) {
		this.spacecraftService = spacecraftService;
	}

	@GetMapping
    public ResponseEntity<Page<SpacecraftResponseDTO>> getAllSpacecrafts(Pageable pageable) {
        Page<SpacecraftResponseDTO> spacecrafts = spacecraftService.findAll(pageable);
        return ResponseEntity.ok(spacecrafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDTO> getSpacecraftById(@PathVariable Long id) {
    	SpacecraftResponseDTO spacecraft = spacecraftService.findById(id);
        return new ResponseEntity<>(spacecraft, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<SpacecraftResponseDTO> getSpacecraftsByName(@RequestParam String name) {
        return spacecraftService.findByNameContaining(name);
    }

    
    @PostMapping
    public ResponseEntity<SpacecraftResponseDTO> createSpacecraft(@Valid @RequestBody SpacecraftRequestDTO spacecraftRequestDTO) {
        try {
            SpacecraftResponseDTO createdSpacecraft = spacecraftService.save(spacecraftRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSpacecraft);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpacecraftResponseDTO> updateSpacecraft(@PathVariable Long id, @Valid @RequestBody SpacecraftRequestDTO spacecraftRequestDTO) {
        SpacecraftResponseDTO updatedSpacecraft = spacecraftService.update(id, spacecraftRequestDTO);
        return ResponseEntity.ok(updatedSpacecraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable Long id) {
        spacecraftService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
