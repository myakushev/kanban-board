package com.myakushev.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Это класс-запускатель тестов в стиле JUnit 5.
 * Он использует движок Cucumber через JUnit Platform Suite Engine.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Указывает, где искать .feature файлы
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.myakushev")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber.html, json:target/cucumber.json")
public class CucumberTest {
    // Этот класс должен быть пустым
}