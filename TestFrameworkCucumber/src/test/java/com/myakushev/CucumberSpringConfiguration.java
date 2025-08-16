package com.myakushev;

import com.myakushev.config.AppConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Этот класс связывает Cucumber с контекстом Spring.
 * Аннотация @CucumberContextConfiguration сообщает Cucumber, что это конфигурационный класс.
 * Аннотация @SpringBootTest запускает полный контекст Spring Boot для наших тестов.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = CucumberSpringConfiguration.class)
@EnableConfigurationProperties(AppConfig.class)
@ComponentScan(basePackages = "com.myakushev")
@EnableAutoConfiguration
public class CucumberSpringConfiguration {
    // Этот класс может быть пустым. Его задача - содержать аннотации.
}