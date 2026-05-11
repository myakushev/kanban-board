package com.myakushev.api;

import com.myakushev.BaseTest;
import com.myakushev.api.generators.TestDataStorage;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest extends BaseTest {

    @BeforeMethod
    public void beforeApiTest() {
        testDataStorage = TestDataStorage.getStorage();
    }
}
