package com.myakushev.api.requests.checked;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.models.tasks.TaskRequest;
import com.myakushev.api.requests.BaseRequest;
import com.myakushev.api.requests.CrudInterface;
import com.myakushev.api.requests.unchecked.UncheckedKanban;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;


public class CheckedKanban extends BaseRequest implements CrudInterface {
    public CheckedKanban(RequestSpecification spec) {
        super(spec);
    }

    @Override
    @Step("Checked create Kanban request {obj}")
    public Kanban create(Object obj) {
        return new UncheckedKanban(spec)
                .create(obj)
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(Kanban.class);
    }

    @Override
    public Kanban get(Integer id) {
        return new UncheckedKanban(spec)
                .get(id)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Kanban.class);
    }

    @Override
    public Kanban get(String title) {
        return new UncheckedKanban(spec)
                .get(title)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Kanban.class);
    }

    @Override
    public Kanban update(Integer id, Object obj) {
        return new UncheckedKanban(spec)
                .update(id, obj)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Kanban.class);
    }

    @Override
    public Kanban delete(Integer id) {
        return new UncheckedKanban(spec)
                .delete(id)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().as(Kanban.class);
    }

    public Kanban addNewTaskToKanban(Integer kanbanId, TaskRequest taskRequest) {
        return new UncheckedKanban(spec)
                .addNewTaskToKanban(kanbanId, taskRequest)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(Kanban.class);
    }
}
