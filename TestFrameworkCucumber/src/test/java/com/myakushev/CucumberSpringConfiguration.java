package com.myakushev;

import com.myakushev.config.AppConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@CucumberContextConfiguration
@SpringBootTest(classes = CucumberSpringConfiguration.class)
@EnableConfigurationProperties(AppConfig.class)
@ComponentScan(basePackages = "com.myakushev")
// *** ЭТО КЛЮЧЕВОЕ ИЗМЕНЕНИЕ ***
// Мы говорим Spring: не пытайся автоматически создавать DataSource, TransactionManager и JdbcTemplate.
// Мы создадим их сами, когда нам это понадобится.
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        JdbcTemplateAutoConfiguration.class
})
public class CucumberSpringConfiguration {
    // Этот класс может быть пустым. Его задача - содержать аннотации.
}