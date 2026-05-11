package com.myakushev.api.requests;

import com.myakushev.api.requests.unchecked.UncheckedKanban;
import com.myakushev.api.requests.unchecked.UncheckedTask;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private UncheckedKanban uncheckedKanban;
    private UncheckedTask uncheckedTask;

    public UncheckedRequests(RequestSpecification spec) {
        this.uncheckedKanban = new UncheckedKanban(spec);
        this.uncheckedTask = new UncheckedTask(spec);
    }
}
