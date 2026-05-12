package com.myakushev.api.generators;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;

    private TestDataStorage() {
        testDataList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    @Step("Add test data to storage: {testData}")
    public TestData addTestData(TestData testData) {
        testDataList.add(testData);
        return testData;
    }

    @Step("Generate and Add test data to storage")
    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        getStorage().addTestData(testData);
        return testData;
    }

}
