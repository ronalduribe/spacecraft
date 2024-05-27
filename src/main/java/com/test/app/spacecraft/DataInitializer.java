package com.test.app.spacecraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.app.spacecraft.models.dao.SpacecraftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.test.app.spacecraft.models.entity.Spacecraft;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
	@Autowired
    private SpacecraftRepository spacecraftRepository;
    

    @Override
    public void run(String... args) throws Exception {
        spacecraftRepository.deleteAll();

        List<Spacecraft> spacecrafts = Arrays.asList(
                new Spacecraft("X-Wing", "Star Wars"),
                new Spacecraft("Millennium Falcon", "Star Wars"),
                new Spacecraft("USS Enterprise", "Star Trek"),
                new Spacecraft("Serenity", "Firefly"),
                new Spacecraft("Galactica", "Battlestar Galactica"),
                new Spacecraft("Discovery One", "2001: A Space Odyssey"),
                new Spacecraft("Nostromo", "Alien"),
                new Spacecraft("Event Horizon", "Event Horizon"),
                new Spacecraft("Eagle 5", "Spaceballs"),
                new Spacecraft("Heart of Gold", "The Hitchhiker's Guide to the Galaxy"),
                new Spacecraft("Red Dwarf", "Red Dwarf"),
                new Spacecraft("Andromeda Ascendant", "Andromeda"),
                new Spacecraft("Bebop", "Cowboy Bebop"),
                new Spacecraft("Swordfish II", "Cowboy Bebop"),
                new Spacecraft("Slave I", "Star Wars"),
                new Spacecraft("Executor", "Star Wars"),
                new Spacecraft("Jupiter 2", "Lost in Space"),
                new Spacecraft("Razor Crest", "The Mandalorian"),
                new Spacecraft("Thunderbird 3", "Thunderbirds"),
                new Spacecraft("Deep Space Nine", "Star Trek: Deep Space Nine"),
                new Spacecraft("Defiant", "Star Trek: Deep Space Nine"),
                new Spacecraft("Ghost", "Star Wars Rebels")
        );

        spacecraftRepository.saveAll(spacecrafts);

        logger.info("Datos de naves espaciales inicializados en la base de datos.");
    }
}
