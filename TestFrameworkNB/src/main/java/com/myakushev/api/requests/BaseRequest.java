package com.myakushev.api.requests;

import io.restassured.specification.RequestSpecification;

public abstract class BaseRequest {
    protected RequestSpecification spec;

    public BaseRequest(RequestSpecification spec) {
        this.spec = spec;
    }
}
