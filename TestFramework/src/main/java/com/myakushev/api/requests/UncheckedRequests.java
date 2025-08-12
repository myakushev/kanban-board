package com.myakushev.api.requests;

import com.myakushev.api.requests.unchecked.UncheckedKanbans;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private UncheckedKanbans kanbanRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.kanbanRequest = new UncheckedKanbans(spec);
    }

}
