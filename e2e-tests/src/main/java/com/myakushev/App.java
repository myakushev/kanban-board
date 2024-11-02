package com.myakushev;

import com.microsoft.playwright.*;

public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            Page page = browser.newPage();
//            page.navigate("http://playwright.dev");
            page.navigate("http://localhost:4200/");
            System.out.println(page.title());
        }
    }
}
