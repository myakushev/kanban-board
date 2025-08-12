package com.myakushev.execution;

import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import com.myakushev.config.EnvConfig;
import com.myakushev.config.EnvConfigProvider;
import com.myakushev.execution.enums.RequestType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static io.restassured.RestAssured.given;

public class HttpBaseClient {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected String baseUrl = "";
    private final EnvConfig envConfig = EnvConfigProvider.getInstance(); // should be deleted from this class - should be in context or somewhere else


    public HttpBaseClient() {
    }

    public HttpBaseClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder
                .setBaseUri(baseUrl)//                // TODO: filer undestanding
                .addFilters(List.of(
                    new RequestLoggingFilter(),
                    new ResponseLoggingFilter(),
//                    new AllureRestAssured(),
                    new SwaggerCoverageRestAssured(new FileSystemOutputWriter(Paths
                            .get("target/" + OUTPUT_DIRECTORY)))
                ))

                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification noAuthSpec() {
        return reqBuilder().build();
    }

    public Response sendRequest(
            RequestType type, String resource, String body) {
        Response response = null;
        switch (type) {
            case POST:
                response = given()
                   .spec(noAuthSpec())
                   .body(body)
                   .post(resource);
                break;
            case GET:
                response = given()
                        .spec(noAuthSpec())
                        .get(resource);
                break;
        }
        return response;
    }
}
