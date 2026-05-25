package com.myakushev;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.myakushev.api.generators.TestDataStorage;
import com.myakushev.api.requests.CheckedRequests;
import com.myakushev.api.requests.UncheckedRequests;
import com.myakushev.api.spec.Specifications;
import com.myakushev.api.utils.DBCleaner;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.*;

import java.sql.SQLException;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Slf4j
public class BaseTest {

    protected SoftAssertions softy;

    public TestDataStorage testDataStorage;
    public UncheckedRequests uncheckedRequests;
    public CheckedRequests checkedRequests;

    protected static WireMockServer wireMockServer;
    protected static final int WIREMOCK_PORT = 8081;

    public BaseTest() {
        Specifications spec = Specifications.getSpec();
        this.uncheckedRequests = new UncheckedRequests(spec.noAuthSpec());
        this.checkedRequests = new CheckedRequests(spec.noAuthSpec());
    }

    public void cleanDB() {
        try {
            DBCleaner.cleanKanbanSchema(Specifications.getSpec().getDataSource());
        } catch (SQLException e) {
            log.error("e: ", e);
        }
    }

    @BeforeMethod(onlyForGroups = "api")
    public void cleanDBForApi() {
        cleanDB();
    }

    @BeforeGroups(groups = "ui")
    public void cleanDBForUI() {
        cleanDB();
    }

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
    }

    @BeforeSuite
    public void startWireMock() {
        wireMockServer = new WireMockServer(wireMockConfig().port(WIREMOCK_PORT));
//        wireMockServer.start();
        configureFor("localhost", WIREMOCK_PORT);
        wireMockServer.resetAll();
        log.info("✅ WireMock started on port: " + WIREMOCK_PORT);
    }

//    @AfterSuite
//    public void stopWireMock() {
//        if (wireMockServer != null) {
//            wireMockServer.resetAll();
//            System.out.println("🛑 WireMock is reset");
//        }
//    }
}
