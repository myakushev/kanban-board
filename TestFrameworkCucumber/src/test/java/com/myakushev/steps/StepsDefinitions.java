package com.myakushev.steps;

import com.myakushev.db.DatabaseService;
import com.myakushev.execution.WebApiClient;
import com.myakushev.testcontext.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class StepsDefinitions {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScenarioContext scenarioContext;
    @Autowired
    private WebApiClient webApiClient;
    @Autowired
    private DatabaseService databaseService;

    @Given("this is a basic test Scenario.")
    public void aBasicTestScenario() {
        logger.info("Cucumber setup is successful!");
    }

    @When("I send a request to create a new kanban board")
    public void sendCreateKanbanBoardRequest(String body) {
        var response = webApiClient.kanbanApi().createKanbanBoard(body);
        scenarioContext.setResponse(response);
    }

    @Then("the {word} table should contain {int} row")
    public void theTableShouldContainRows(String tableName, int expectedRowCount) {
        List<Map<String, Object>> actualRows = databaseService.selectAllFromTable(tableName);
        logger.info("Found {} rows in table '{}'", actualRows.size(), tableName);
        assertEquals(expectedRowCount, actualRows.size());
    }
}