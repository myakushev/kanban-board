package com.myakushev.execution;

import com.myakushev.config.AppConfig;
import com.myakushev.execution.requests.webapi.KanbanBoardApi;
import org.springframework.stereotype.Component;

@Component // Сообщаем Spring, что это бин
public class WebApiClient {

    private final KanbanBoardApi kanbanBoardApi;

    // Spring автоматически внедрит нужный бин AppConfig
    public WebApiClient(AppConfig appConfig) {
        HttpBaseClient httpClient = new HttpBaseClient(appConfig.getGateways().getRestGatewayUrl());
        this.kanbanBoardApi = new KanbanBoardApi(httpClient);
    }

    public KanbanBoardApi kanbanApi() {
        return kanbanBoardApi;
    }
}