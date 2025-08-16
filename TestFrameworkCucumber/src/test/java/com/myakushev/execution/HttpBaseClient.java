package com.myakushev.execution;

import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import com.myakushev.execution.enums.RequestType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static io.restassured.RestAssured.given;

public class HttpBaseClient {

    private final String baseUrl;
    private final RequestSpecification requestSpecification;

    public HttpBaseClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.requestSpecification = buildRequestSpecification();
    }

    private RequestSpecification buildRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilters(List.of(
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        // new AllureRestAssured(),
                        new SwaggerCoverageRestAssured(new FileSystemOutputWriter(Paths
                                .get("target/" + OUTPUT_DIRECTORY)))
                ))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public Response sendRequest(RequestType type, String resource, String body) {
        Response response = null;
        switch (type) {
            case POST:
                response = given()
                        .spec(requestSpecification)
                        .body(body)
                        .post(resource);
                break;
            case GET:
                // GET-запросы тоже могут иметь тело, но обычно не имеют,
                // поэтому для них нужна перегруженная версия метода без body.
                response = given()
                        .spec(requestSpecification)
                        .get(resource);
                break;
        }
        return response;
    }
}