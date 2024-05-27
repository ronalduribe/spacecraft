package com.test.app.spacecraft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.app.spacecraft.models.entity.Spacecraft;
import com.test.app.spacecraft.models.service.SpacecraftService;

import java.util.List;

@RestController
@RequestMapping("/api/spacecrafts")
public class SpacecraftController {

    @Autowired
    private SpacecraftService spacecraftService;

    @GetMapping
    public ResponseEntity<Page<Spacecraft>> getAllSpacecrafts(Pageable pageable) {
        Page<Spacecraft> spacecrafts = spacecraftService.findAll(pageable);
        return ResponseEntity.ok(spacecrafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spacecraft> getSpacecraftById(@PathVariable Long id) {
        Spacecraft spacecraft = spacecraftService.findById(id);
        return new ResponseEntity<>(spacecraft, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Spacecraft> getSpacecraftsByName(@RequestParam String name) {
        return spacecraftService.findByNameContaining(name);
    }

    @PostMapping
    public ResponseEntity<Spacecraft> createSpacecraft(@RequestBody Spacecraft spacecraft) {
        return new ResponseEntity<>(spacecraftService.save(spacecraft), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spacecraft> updateSpacecraft(@PathVariable Long id, @RequestBody Spacecraft spacecraft) {
        spacecraft.setId(id);
        Spacecraft updatedSpacecraft = spacecraftService.update(spacecraft);
        return new ResponseEntity<>(updatedSpacecraft, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpacecraft(@PathVariable Long id) {
        spacecraftService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
