package com.myakushev.steps;

import com.myakushev.db.DatabaseService;
import com.myakushev.db.DatabaseVerifier; // Импортируем наш новый верификатор
import com.myakushev.execution.WebApiClient;
import com.myakushev.testcontext.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepsDefinitions {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScenarioContext scenarioContext;
    @Autowired
    private WebApiClient webApiClient;
    @Autowired
    private DatabaseVerifier databaseVerifier;

    @Given("the database for service {word} is clean")
    public void theDatabaseForServiceIsClean(String serviceName) {
        scenarioContext.getDatabaseService(serviceName).cleanDatabase(); // можно и так: scenarioContext.getDatabaseService(serviceName).cleanDatabase();
    }

    @When("I create a new kanban board with body:")
    public void iCreateNewBoard(String body) {
        // Вызов в стиле, который вы хотели: client.api().action()
        var response = webApiClient.kanbanApi().createKanbanBoard(body);
        scenarioContext.setResponse(response);
    }

    @Then("the {string} table for kanban boards should contain {int} rows")
    public void theKanbanTableShouldContainRows(String tableName, int expectedRowCount) {
        logger.info("Checking table '{}' for kanban boards...", tableName);
        List<Map<String, Object>> actualRows = databaseVerifier.kanbanDb().selectAllFromTable(tableName);

        assertEquals(expectedRowCount, actualRows.size(),
                "Expected " + expectedRowCount + " row(s) in table '" + tableName + "' but found " + actualRows.size());
    }
}