package com.myakushev.api.generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;

public class RandomData {
    private static final int LENGTH = 10;

    private static int lastId = 0;
    private static long lastTime = 0;

    public static String getString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }

    public static String getLongString(int number) {
        return RandomStringUtils.randomAlphabetic(number);
    }

    public static Integer getRandomID() {
        long currentTime = System.currentTimeMillis();

        if (currentTime == lastTime) {
            lastId++;
            if (lastId > 999) {
                while (currentTime == System.currentTimeMillis()) {
                    Thread.yield();
                }
                currentTime = System.currentTimeMillis();
                lastId = 0;
            }
        } else {
            lastId = 0;
            lastTime = currentTime;
        }

        return (int) ((currentTime % 1000000) * 1000 + lastId);
    }
}