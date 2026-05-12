package com.myakushev.api.spec;

import com.myakushev.api.utils.DataSources;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import javax.sql.DataSource;

public class Specifications {
    private static Specifications spec;

    private Specifications() {};

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    public RequestSpecBuilder reqSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.getProperty("app.apiUrl"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured());
    }

    public RequestSpecification noAuthSpec() {
        return reqSpecBuilder().build();
    }

    public RequestSpecification authSpec() {
        var requestBuilder = reqSpecBuilder();
        // authorization here or some other stuff
        return requestBuilder.build();
    }

    public DataSource getDataSource() {
        return DataSources.postgresHikari(
                Config.getProperty("db.host"),
                Integer.parseInt(Config.getProperty("db.port")),
                Config.getProperty("db.dbname"),
                Config.getProperty("db.username"),
                Config.getProperty("db.password"));
    }
}
