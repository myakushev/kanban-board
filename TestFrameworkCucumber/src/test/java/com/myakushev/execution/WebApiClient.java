package com.myakushev.execution;

import com.myakushev.config.AppConfig;
import com.myakushev.execution.requests.webapi.KanbanBoardApi;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebApiClient {

    private final AppConfig appConfig;
    private final Map<String, HttpBaseClient> clientCache = new ConcurrentHashMap<>();

    public WebApiClient(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    // --- Фабричные методы, привязанные к конкретным сервисам ---

    /**
     * Возвращает API для работы с Kanban-сервисом.
     * Этот метод "знает", что ему нужен "kanban-service".
     */
    public KanbanBoardApi kanbanApi() {
        // Жесткая привязка метода к имени сервиса. Просто и понятно.
        return new KanbanBoardApi(getHttpClientFor("kanban-service"));
    }

    /**
     * В будущем вы добавите сюда другие API.
     * public UserApi userApi() {
     *     return new UserApi(getHttpClientFor("user-service"));
     * }
     */

    // Вспомогательный метод остается без изменений
    private HttpBaseClient getHttpClientFor(String serviceName) {
        return clientCache.computeIfAbsent(serviceName, name -> {
            String gatewayUrl = appConfig.getServiceProperties(name).getGateway();
            return new HttpBaseClient(gatewayUrl);
        });
    }
}