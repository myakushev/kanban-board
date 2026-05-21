package com.myakushev;

import com.myakushev.api.generators.TestDataStorage;
import com.myakushev.api.requests.CheckedRequests;
import com.myakushev.api.requests.UncheckedRequests;
import com.myakushev.api.spec.Specifications;
import com.myakushev.api.utils.DBCleaner;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;

import java.sql.SQLException;

@Slf4j
public class BaseTest {

    protected SoftAssertions softy;

    public TestDataStorage testDataStorage;
    public UncheckedRequests uncheckedRequests;
    public CheckedRequests checkedRequests;

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
    }}
