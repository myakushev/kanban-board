package com.myakushev.execution;

import com.myakushev.execution.enums.RequestType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RequestApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpBaseClient httpBaseClient;

    public RequestApi(HttpBaseClient httpBaseClient) {
        this.httpBaseClient = httpBaseClient;
    }

    protected Response sendPostRequest(String url, String body) {
        return httpBaseClient.sendRequest(
                RequestType.POST,
                url,
                body);
    }

    // Стоит добавить и другие методы для удобства
    protected Response sendGetRequest(String url) {
        return httpBaseClient.sendRequest(RequestType.GET, url, null);
    }
}