package com.myakushev.api.generators;

import java.util.Random;
import java.util.UUID;

import com.myakushev.api.enums.Color;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private static final int LENGTH = 10;

    public static String getRandomString() {
        return "test_" + RandomStringUtils.randomAlphabetic(LENGTH);
    }
    public static String getRandomLongString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getRandomNumberString(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String uniqueId() {
        return UUID.randomUUID().toString();
    }

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
        Random random = new Random();
        T[] enumConstants = clazz.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)];
    }
}
