package com.myakushev.steps;

import com.myakushev.db.DatabaseService;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CucumberHooks {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static volatile boolean isDatabaseCleaned = false;

    @Autowired
    private DatabaseService databaseService;

    @Before(order = 0)
    public void setUp() {
        synchronized (CucumberHooks.class) {
            if (!isDatabaseCleaned) {
                databaseService.cleanDatabase();
                isDatabaseCleaned = true;
            }
        }
    }
}