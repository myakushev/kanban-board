package com.myakushev.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

/**
 * Reads *.yaml files with test's configuration in 'resources' folder
 */
public final class ConfigReader {

    private ConfigReader() {
        // utils class
    }

    /**
     * Loads environment configuration for tests
     * Sets default values for ConditionWaiter
     *
     * @return deserialized EnvConfig object
     */

    public static EnvConfig readEnvConfig(String fileName) {
        EnvConfig envConfig = readConfigYAML(fileName, EnvConfig.class);
        return envConfig;
    }

    private static <T> T readConfigYAML(String yamlFileName, Class<T> valueType) {
        try {
            return new ObjectMapper(new YAMLFactory()).readValue(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(yamlFileName),
                    valueType);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to read yaml file '%s'", yamlFileName), e);
        }
    }
}
