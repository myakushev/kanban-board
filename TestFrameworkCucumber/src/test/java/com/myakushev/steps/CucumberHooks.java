package com.myakushev.steps;

import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberHooks {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before(order = 0)
    public void setUp() {
        logger.info("--- Starting new scenario ---");
        // Логика очистки базы данных перенесена в явный шаг Gherkin для прозрачности
    }
}