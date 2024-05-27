package com.test.app.spacecraft.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.test.app.spacecraft.controllers.SpacecraftController.getSpacecraftById(..)) && args(id)")
    public void logNegativeId(Long id) {
        if (id < 0) {
            logger.warn("Request with negative ID: " + id);
        }
    }
}
