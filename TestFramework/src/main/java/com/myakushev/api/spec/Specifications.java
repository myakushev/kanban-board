package com.myakushev.api.spec;

import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import com.myakushev.utils.Config;
import com.myakushev.utils.DataSources;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import javax.sql.DataSource;
import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

public final class Specifications {
    private static Specifications spec;

    private Specifications() {}

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder
                .setBaseUri(Config.getProperty("apiURI"))
//                // TODO: filer undestanding
//                .addFilters(List.of(
//                    new RequestLoggingFilter(),
//                    new ResponseLoggingFilter(),
////                    new AllureRestAssured(),
//                    new SwaggerCoverageRestAssured(new FileSystemOutputWriter(Paths
//                            .get("target/" + OUTPUT_DIRECTORY)))
//                ))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification noAuthSpec() {
        return reqBuilder().build();
    }

    public RequestSpecification authSpec() {
        var requestBuilder = reqBuilder();
        // here you can add any auth data to requestBuilder
        return requestBuilder.build();
    }

    public DataSource getDataSource() {
        return DataSources.postgresHikari("localhost", 5432, "kanban", "kanban", "kanban");
    }
}
