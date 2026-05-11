import api.KanbanCreateReq;
import api.KanbanCreateRes;
import api.Specifications;
import db.DBService;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class KanbanBoardTest {
    @BeforeAll
    public static void setup(){
//        Specifications.installSpecification(Specifications.reguestSpec("http://localhost:8080/api/"), Specifications.responseSpec(201));
//        baseURI = "http://localhost:8080/api/";
        DBService.cleanDatabase();
    }

    @Test
    public void getKanbanTest() {
        Specifications.installSpecification(Specifications.reguestSpec("http://localhost:8080/api/"), Specifications.responseSpec(200));
        List<KanbanCreateRes> kanban = given()
                .when().log().all()
                .contentType(ContentType.JSON)
                .get("kanbans/")
                .then().log().all()
                .extract().body().as(new TypeRef<List<KanbanCreateRes>>() {});
    }

    @Test
    public void createKanbanTest() {
        Specifications.installSpecification(Specifications.reguestSpec("http://localhost:8080/api/"), Specifications.responseSpec(201));
        KanbanCreateReq kanbanNew = new KanbanCreateReq("test5");
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
