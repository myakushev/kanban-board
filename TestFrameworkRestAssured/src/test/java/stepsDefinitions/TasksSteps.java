package stepsDefinitions;

import api.KanbanCreateReq;
import api.KanbanCreateRes;
import api.Specifications;
import api.TaskReq;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class TasksSteps {
    public int createTask() {
        Specifications.installSpecification(Specifications.reguestSpec("http://localhost:8080/api/"), Specifications.responseSpec(201));
        TaskReq taskNew = new TaskReq.builder()
                .name()

        KanbanCreateRes kanban = given()
                .body(kanbanNew)
                .when().log().all()
                .post("kanbans/")
                .then().log().all()
                .extract().as(KanbanCreateRes.class);

        Assertions.assertEquals(kanban.getTitle(), kanbanNew.getTitle());
        Assertions.assertNull(kanban.getTaskRes());

        System.out.println(kanban);
    }
}
