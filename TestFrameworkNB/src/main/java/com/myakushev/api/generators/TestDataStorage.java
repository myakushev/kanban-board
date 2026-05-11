package com.myakushev.api.generators;

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

    public TestData addTestData(TestData testData) {
        testDataList.add(testData);
        return testData;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        getStorage().addTestData(testData);
        return testData;
    }

}
