package com.myakushev.api.requests;

import com.myakushev.api.requests.checked.CheckedKanbans;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private CheckedKanbans kanbanRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.kanbanRequest = new CheckedKanbans(spec);
    }
}
