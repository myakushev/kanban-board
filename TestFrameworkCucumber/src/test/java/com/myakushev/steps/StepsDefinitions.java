package com.myakushev.steps;

import com.myakushev.config.EnvConfig;
import com.myakushev.config.EnvConfigProvider;
import com.myakushev.execution.Apis;
import com.myakushev.testcontext.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StepsDefinitions {
    private final Logger logger = LogManager.getLogger(getClass());
    private final EnvConfig envConfig = EnvConfigProvider.getInstance(); // should be deleted from this class - should be in context or somewhere else
//    private final TestContext testContext = TestContext.getTestContext();

    private Apis apis;

    public StepsDefinitions() {
        apis = Apis.getApis();
    }

    @Given("this is a basic test Scenario.")
    public void basicTest() {
        logger.info("Cucumber setup is successful!");
        String url = envConfig.getGateways().getRestGatewayUrl();
        logger.info("First param: {}", url);
        int db = envConfig.getJdbc().get("kanban").getPortNumber();
        logger.info("Second param: {}", db);
    }

    @And("send {word} request")
    public void sendRequestWithBody(String requestName, String body) {
        apis.get(requestName).executeRequest(body);
    }
}
