package com.myakushev.api.requests;

import com.myakushev.api.requests.checked.CheckedKanban;
import com.myakushev.api.requests.checked.CheckedTask;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private CheckedKanban kanbanRequest;
    private CheckedTask taskRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.kanbanRequest = new CheckedKanban(spec);
        this.taskRequest = new CheckedTask(spec);
    }
}
