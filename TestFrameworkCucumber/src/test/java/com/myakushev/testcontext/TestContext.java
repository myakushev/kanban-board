package com.myakushev.testcontext;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Response response;

    private static TestContext context;

    public static TestContext getTestContext() {
        if (context == null) {
            context = new TestContext();
        }
        return context;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
