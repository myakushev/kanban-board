package com.myakushev;

import com.myakushev.api.generators.TestDataStorage;
import com.myakushev.api.requests.CheckedRequests;
import com.myakushev.api.requests.UncheckedRequests;
import com.myakushev.api.spec.Specifications;
import com.myakushev.api.utils.DBCleaner;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

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

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();

        try{
            DBCleaner.cleanKanbanSchema(Specifications.getSpec().getDataSource());
        } catch (Exception e) {
            log.error("e: ", e);
        }
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();;
    }}
