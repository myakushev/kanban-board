package com.myakushev.api.requests.unchecked;

import com.myakushev.api.models.Kanban;
import com.myakushev.api.models.KanbanDTO;
import com.myakushev.api.requests.BaseRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedKanbans extends BaseRequest {
    public static final String KANBAN_API = "/kanbans";

    public UncheckedKanbans(RequestSpecification spec) {
        super(spec);
    }

    public Response getByTitle(String title) {
        return given()
                .spec(spec)
                .param("title", title)
                .get(KANBAN_API);
    }

    public Response createKanban(KanbanDTO kanbanDTO) {
        return given()
                .spec(spec)
                .body(kanbanDTO)
                .post(KANBAN_API + "/");
    }
}
