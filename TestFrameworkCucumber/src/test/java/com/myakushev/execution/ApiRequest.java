package com.myakushev.execution;

import com.myakushev.testcontext.TestContext;
import io.restassured.response.Response;

import java.util.function.Function;

public class ApiRequest {

    private TestContext testContext;
    private Function<String, Response> requestWithBody;

    public ApiRequest(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Adds api function that excepts String body
     * E.g. - mobileApiClient.statementApi().getStatementSummary(String body)
     *
     * @param requestWithBody api function that excepts String body, executes call and returns HttpResponse
     */
    public ApiRequest setApi(Function<String, Response> requestWithBody) {
        this.requestWithBody = requestWithBody;
        return this;
    }

    /**
     * Executes api call for function that was setup in setApi method.
     * Replace holders & substitutes params in request body before request sending.
     *
     * @param body request body
     */
    public void executeRequest(String body) {
        testContext.setResponse(requestWithBody.apply(body));
//        saveParams();
    }

    public Function<String, Response> getRequestWithBody() {
        return requestWithBody;
    }


    /**
     * Saves parameters into test context after receiving response
     */
//    private void saveParams() {
//        params.forEach(p -> StepUtils.extractParamFromResponse(testContext, p));
//    }
}
