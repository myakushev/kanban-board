package com.myakushev.execution;

import com.myakushev.config.EnvConfig;
import com.myakushev.config.EnvConfigProvider;
import com.myakushev.testcontext.TestContext;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class Apis {
    private final TestContext testContext = TestContext.getTestContext();
    private final EnvConfig envConfig = EnvConfigProvider.getInstance();
//    private final WebApiClient webApiClient;

    private static Apis apisObject;
    private Map<String, ApiRequest> apis = new HashMap<>();

    private Apis() {
        setupWebApiClient();
    }

    public static Apis getApis() {
        if (apisObject == null) {
            apisObject = new Apis();
        }
        return apisObject;
    }

    private void setupWebApiClient() {
        addApi("create-kanban-board", WebApiClient.getWebApiClient(envConfig.getGateways().getRestGatewayUrl()).kanbanApi()::createKanbanBoard);
    }

    private void addApi(String apiName, Function<String, Response> requestWithBody) {
        apis.put(apiName, new ApiRequest(testContext).setApi(requestWithBody));
    }

    public ApiRequest get(String requestName) {
        if (!apis.containsKey(requestName)) {

            throw new IllegalArgumentException(
                    String.format("Specified api not found in existed apis list. Specified - '%s', existed - '%s'",
                            requestName, apis.keySet()));
        }
        return apis.get(requestName);
    }
}
