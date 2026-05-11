package com.myakushev.api.requests.unchecked;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.models.tasks.Task;
import com.myakushev.api.models.tasks.TaskRequest;
import com.myakushev.api.requests.BaseRequest;
import com.myakushev.api.requests.CrudInterface;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedKanban extends BaseRequest implements CrudInterface {
    private static final String KANBAN_API = "/kanbans";

    public UncheckedKanban (RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec).log().all()
                .body(obj)
                .post(KANBAN_API + "/");
    }

    @Override
    public Response get(Integer id) {
        return given().spec(spec).log().all()
                .get(KANBAN_API + "/" + id);
    }

    @Override
    public Response get(String title) {
        return given().spec(spec).log().all()
                .param("title", title)
                .get(KANBAN_API);
    }

    @Override
    public Response update(Integer id, Object obj) {
        return given().spec(spec).log().all()
                .body(obj)
                .put(KANBAN_API + "/" + id);
    }

    @Override
    public Response delete(Integer id) {
        return given()
                .spec(spec).log().all()
                .delete(KANBAN_API + "/" + id);
    }

    public Response addNewTaskToKanban(Integer kanbanId, TaskRequest taskRequest) {
        return given()
                .spec(spec).log().all()
                .body(taskRequest)
                .post(KANBAN_API + "/" + kanbanId + "/tasks/");
    }
}
