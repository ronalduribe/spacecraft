package com.test.app.spacecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCaching
public class SpacecracfApplication {
    
    public static void main(String [] args){
        SpringApplication.run(SpacecracfApplication.class, args);
    }

}