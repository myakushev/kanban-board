package com.myakushev.ui;

import com.codeborne.selenide.Configuration;
import utils.Config;

public abstract class Page {
    static {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1280";
        Configuration.headless = Boolean.parseBoolean(Config.getProperty("headless"));
    }
}
