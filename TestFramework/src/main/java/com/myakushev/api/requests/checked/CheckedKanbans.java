package com.myakushev.api.requests.checked;

import com.myakushev.api.models.Kanban;
import com.myakushev.api.models.KanbanDTO;
import com.myakushev.api.requests.BaseRequest;
import com.myakushev.api.requests.unchecked.UncheckedKanbans;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedKanbans extends BaseRequest {

    public CheckedKanbans(RequestSpecification spec) {
        super(spec);
    }

    public Kanban create(KanbanDTO obj) {
        return new UncheckedKanbans(spec)
                .createKanban(obj)
                .then().assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract().as(Kanban.class);
    }
}
