package com.yakushev;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.myakushev.App;
import com.myakushev.DebugClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class AppTest {

    @Autowired
    private DebugClass debugItem;

    @Test
    void training1Test() {
        System.out.println("Test Start");

        debugItem.message();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setSlowMo(50));
            Page page = browser.newPage();
//            page.navigate("http://playwright.dev");
            page.navigate("http://localhost:4200/");
            System.out.println(page.title());
        }
    }
}
