package com.myakushev.execution;

import com.myakushev.execution.requests.webapi.KanbanBoardApi;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WebApiClient {
    private final HttpBaseClient httpClient;
    protected Map<String, RequestApi> requestsApis = new HashMap<>();
    private static WebApiClient webApiClient;

    private final static String KANBAN_BOARD_API = "KANBAN_BOARD_API";

    public WebApiClient(String baseUrl) {
        httpClient = new HttpBaseClient(baseUrl);
        requestsApis.put(KANBAN_BOARD_API, new KanbanBoardApi(httpClient));
    }

    public static WebApiClient getWebApiClient(String baseUrl) {
        if (webApiClient == null) {
            webApiClient = new WebApiClient(baseUrl);
        }
        return webApiClient;
    }

    public KanbanBoardApi kanbanApi() {
        return (KanbanBoardApi) requestsApis.get(KANBAN_BOARD_API);
    }

}
