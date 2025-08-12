package com.myakushev.api;

import com.myakushev.api.generators.TestDataStorage;
import com.myakushev.api.requests.CheckedRequests;
import com.myakushev.api.requests.UncheckedRequests;
import com.myakushev.api.spec.Specifications;
import com.myakushev.utils.DBCleaner;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.sql.SQLException;

public class BaseTest {
    protected SoftAssertions softy;

    public TestDataStorage testDataStorage;
    public UncheckedRequests uncheckedRequests = new UncheckedRequests(Specifications.getSpec().noAuthSpec());
    public CheckedRequests checkedRequests = new CheckedRequests(Specifications.getSpec().noAuthSpec());

    @BeforeSuite
    public void cleanDB() throws SQLException {
        DBCleaner.cleanKanbanSchema(Specifications.getSpec().getDataSource());
    }

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getTestDataStorage();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
    }
}
