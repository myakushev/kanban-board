package com.myakushev.api.generators;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> testDataList;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getTestDataStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public TestData addTestData(TestData testDataItem) {
        getTestDataStorage().testDataList.add(testDataItem);
        return testDataItem;
    }

    public TestData addTestData() {
        var testDataItem = TestDataGenerator.generate();
        addTestData(testDataItem);
        return testDataItem;
    }
}
