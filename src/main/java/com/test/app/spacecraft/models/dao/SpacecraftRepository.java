package com.test.app.spacecraft.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.app.spacecraft.models.entity.Spacecraft;

import java.util.List;

@Repository
public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
    List<Spacecraft> findByNameContaining(String name);
}