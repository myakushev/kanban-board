package com.myakushev.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import utils.Config;

public abstract class Page {
    static {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1280";
        Configuration.headless = Boolean.parseBoolean(Config.getProperty("headless"));
        Configuration.timeout = 9000;
    }

    public void refreshPage() {
        Selenide.refresh();
    }
}
