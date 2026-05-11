package com.myakushev.api.requests.checked;

import com.myakushev.api.models.kanban.Kanban;
import com.myakushev.api.models.tasks.Task;
import com.myakushev.api.requests.BaseRequest;
import com.myakushev.api.requests.CrudInterface;
import com.myakushev.api.requests.unchecked.UncheckedKanban;
import com.myakushev.api.requests.unchecked.UncheckedTask;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedTask extends BaseRequest implements CrudInterface {
    public CheckedTask(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Task create(Object obj) {
        return new UncheckedTask(spec)
                .create(obj)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(Task.class);
    }

    @Override
    public Object get(Integer id) {
        return new UncheckedTask(spec)
                .get(id)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Task.class);
    }

    @Override
    public Object get(String title) {
        return new UncheckedTask(spec)
                .get(title)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Task.class);
    }

    @Override
    public Task update(Integer id, Object obj) {
        return new UncheckedKanban(spec)
                .update(id, obj)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Task.class);
    }

    @Override
    public Object delete(Integer id) {
        return new UncheckedKanban(spec)
                .delete(id)
                .then().log().all()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().as(Task.class);
    }
}
