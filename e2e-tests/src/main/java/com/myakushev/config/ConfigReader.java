package com.myakushev.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * Reads *.yaml files with test's configuration in 'resources' folder
 */
public final class ConfigReader {

    private ConfigReader() {
        // utils class
    }

    /**
     * Loads environment configuration for tests (connection settings; db, redis configs etc.).
     * Sets default values for ConditionWaiter
     *
     * @return deserialized EnvConfig object
     */

    public static EnvConfig readEnvConfig(String fileName) {
        System.out.println("start reading config");
        System.out.println(fileName);
        EnvConfig envConfig = readConfigYAML(fileName, EnvConfig.class);
        System.out.println(envConfig.getWebGatewayUrl());
        System.out.println(envConfig.getJdbc().get("backend").getUser());
        System.out.println("config was read");
        return envConfig;
    }

    private static <T> T readConfigYAML(String yamlFileName, Class<T> valueType) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(new File("src/main/resources/" + yamlFileName), valueType);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to read yaml file '%s'", yamlFileName), e);
        }
    }
}

