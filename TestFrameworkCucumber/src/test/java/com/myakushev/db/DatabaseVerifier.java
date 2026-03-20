package com.myakushev.db;

import com.myakushev.testcontext.ScenarioContext;
import org.springframework.stereotype.Component;

/**
 * Компонент, предоставляющий доступ к DatabaseService для конкретных сервисов.
 * Аналог WebApiClient, но для работы с БД.
 */
@Component
public class DatabaseVerifier {

    private final ScenarioContext scenarioContext;

    public DatabaseVerifier(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    /**
     * Возвращает сервис для работы с БД Kanban-сервиса.
     * Этот метод "знает", что ему нужен "kanban-service".
     */
    public DatabaseService kanbanDb() {
        return scenarioContext.getDatabaseService("kanban-service");
    }

    /**
     * В будущем вы добавите сюда доступ к другим БД.
     * public DatabaseService userDb() {
     *     return scenarioContext.getDatabaseService("user-service");
     * }
     */
}