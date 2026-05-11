import api.Specifications;
import db.DBService;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TasksTest {

    @BeforeAll
    public static void setup() {
        DBService.cleanDatabase();
    }

    @Test
    public void createTaskTest() {
        Specifications.installSpecification(Specifications.reguestSpec("http://localhost:8080/api/"), Specifications.responseSpec(201));

    }
}
