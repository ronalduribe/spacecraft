package com.test.app.spacecraft.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.test.app.spacecraft.models.dto.SpacecraftDTO;
import com.test.app.spacecraft.models.service.SpacecraftService;

@RestController
@RequestMapping("/api/spacecrafts")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;
    

    public SpacecraftController(SpacecraftService spacecraftService) {
		this.spacecraftService = spacecraftService;
	}

	@GetMapping
    public ResponseEntity<Page<SpacecraftDTO>> getAllSpacecrafts(Pageable pageable) {
        Page<SpacecraftDTO> spacecrafts = spacecraftService.findAll(pageable);
        return ResponseEntity.ok(spacecrafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpacecraftDTO> getSpacecraftById(@PathVariable Long id) {
    	SpacecraftDTO spacecraft = spacecraftService.findById(id);
        return new ResponseEntity<>(spacecraft, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<SpacecraftDTO> getSpacecraftsByName(@RequestParam String name) {
        return spacecraftService.findByNameContaining(name);
    }

    @PostMapping
    public ResponseEntity<SpacecraftDTO> createSpacecraft(@RequestBody SpacecraftDTO spacecraft) {
        return new ResponseEntity<>(spacecraftService.save(spacecraft), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpacecraftDTO> updateSpacecraft(@PathVariable Long id, @RequestBody SpacecraftDTO spacecraft) {
        spacecraft.setId(id);
        SpacecraftDTO updatedSpacecraft = spacecraftService.update(spacecraft);
        return new ResponseEntity<>(updatedSpacecraft, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable Long id) {
        spacecraftService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
