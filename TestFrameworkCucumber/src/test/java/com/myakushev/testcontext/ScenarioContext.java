package com.myakushev.testcontext;

import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@ScenarioScope // Этот бин будет пересоздаваться для каждого нового сценария!
public class ScenarioContext {

    private Response response;

    public void clear() {
        response = null;
    }
}