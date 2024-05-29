package com.test.app.spacecraft;

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
	
    private final SpacecraftRepository spacecraftRepository;

    public DataInitializer(SpacecraftRepository spacecraftRepository) {
		super();
		this.spacecraftRepository = spacecraftRepository;
	}

	@Override
    public void run(String... args) throws Exception {
        spacecraftRepository.deleteAll();

        List<Spacecraft> spacecrafts = Arrays.asList(
                new Spacecraft(1L, "X-Wing", "Star Wars"),
                new Spacecraft(2L, "Millennium Falcon", "Star Wars"),
                new Spacecraft(3L, "USS Enterprise", "Star Trek"),
                new Spacecraft(4L, "Serenity", "Firefly"),
                new Spacecraft(5L, "Galactica", "Battlestar Galactica"),
                new Spacecraft(6L, "Discovery One", "2001: A Space Odyssey"),
                new Spacecraft(7L, "Nostromo", "Alien"),
                new Spacecraft(8L, "Event Horizon", "Event Horizon"),
                new Spacecraft(9L, "Eagle 5", "Spaceballs"),
                new Spacecraft(10L, "Heart of Gold", "The Hitchhiker's Guide to the Galaxy"),
                new Spacecraft(11L, "Red Dwarf", "Red Dwarf"),
                new Spacecraft(12L, "Andromeda Ascendant", "Andromeda"),
                new Spacecraft(13L, "Bebop", "Cowboy Bebop"),
                new Spacecraft(14L, "Swordfish II", "Cowboy Bebop"),
                new Spacecraft(15L, "Slave I", "Star Wars"),
                new Spacecraft(16L, "Executor", "Star Wars"),
                new Spacecraft(17L, "Jupiter 2", "Lost in Space"),
                new Spacecraft(18L, "Razor Crest", "The Mandalorian"),
                new Spacecraft(19L, "Thunderbird 3", "Thunderbirds"),
                new Spacecraft(20L, "Deep Space Nine", "Star Trek: Deep Space Nine"),
                new Spacecraft(21L, "Defiant", "Star Trek: Deep Space Nine"),
                new Spacecraft(22L, "Ghost", "Star Wars Rebels")
        );

        spacecraftRepository.saveAll(spacecrafts);

        logger.info("Datos de naves espaciales inicializados en la base de datos.");
    }
}
