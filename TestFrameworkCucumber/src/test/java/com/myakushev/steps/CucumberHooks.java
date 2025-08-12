package com.myakushev.steps;

import com.myakushev.config.EnvConfigProvider;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CucumberHooks {

    private static final String SPLITTER = "======================";
    private final Logger logger = LogManager.getLogger(CucumberHooks.class);

    public CucumberHooks() {
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        EnvConfigProvider.init("env-config.yaml");
        logger.info("{} SCENARIO '{}' {}", SPLITTER, scenario.getName(), SPLITTER);
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
//        getTestContext().clear();
        logger.info("{} END OF SCENARIO '{}' {}\n\n\n\n\n", SPLITTER, scenario.getName(), SPLITTER);
    }

}
