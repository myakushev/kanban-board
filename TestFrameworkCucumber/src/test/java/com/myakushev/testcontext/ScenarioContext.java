package com.myakushev.testcontext;

import com.myakushev.config.AppConfig;
import com.myakushev.db.DatabaseService;
import com.zaxxer.hikari.HikariDataSource;
import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
@ScenarioScope
public class ScenarioContext {

    private Response response;

    private final AppConfig appConfig;
    private final Map<String, DatabaseService> dbServiceCache = new ConcurrentHashMap<>();

    @Autowired
    public ScenarioContext(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Получает сервис для работы с БД для указанного сервиса.
     * Если сервис еще не создан, создает его и кэширует.
     */
    public DatabaseService getDatabaseService(String serviceName) {
        return dbServiceCache.computeIfAbsent(serviceName, this::createDatabaseService);
    }

    /**
     * Вспомогательный метод для создания DatabaseService на лету.
     */
    private DatabaseService createDatabaseService(String serviceName) {
        AppConfig.DatabaseProperties dbProps = appConfig.getServiceProperties(serviceName).getDb();
        if (dbProps == null) {
            throw new IllegalArgumentException("Database configuration for service '" + serviceName + "' not found in application.yml.");
        }

        DataSource dataSource = createDataSource(dbProps);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return new DatabaseService(jdbcTemplate, dbProps.getSchema());
    }

    private DataSource createDataSource(AppConfig.DatabaseProperties dbProps) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbProps.getUrl());
        dataSource.setUsername(dbProps.getUsername());
        dataSource.setPassword(dbProps.getPassword());
        dataSource.setDriverClassName(dbProps.getDriverClassName());
        dataSource.setSchema(dbProps.getSchema());
        return dataSource;
    }
}