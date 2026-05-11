package com.myakushev.api.requests.unchecked;

import com.myakushev.api.requests.BaseRequest;
import com.myakushev.api.requests.CrudInterface;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedTask extends BaseRequest implements CrudInterface {
    private static final String TASK_API = "/tasks";

    public UncheckedTask(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object task) {
        return given()
                .spec(spec).log().all()
                .body(task)
                .post(TASK_API);
    }

    @Override
    public Response get(Integer id) {
        return given()
                .spec(spec).log().all()
                .get(TASK_API + "/" + id);
    }

    @Override
    public Response get(String title) {
        return given()
                .spec(spec).log().all()
                .param("title", title)
                .get(TASK_API);
    }

    @Override
    public Response update(Integer id, Object obj) {
        return given().spec(spec)
                .body(obj).log().all()
                .put(TASK_API + "/" + id);
    }

    @Override
    public Response delete(Integer id) {
        return given().log().all()
                .delete(TASK_API + "/" + id);
    }
}
