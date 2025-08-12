package com.myakushev.config;

public final class EnvConfigProvider {

    private static volatile EnvConfig instance;
    private static String configFileName;

    private EnvConfigProvider() {
    }

    public static void init(String fileName) {
        if (instance != null) {
            throw new IllegalStateException("EnvConfig already initialized");
        }
        configFileName = fileName;
    }

    public static EnvConfig getInstance() {
        if (instance == null) {
            synchronized (EnvConfigProvider.class) {
                if (instance == null) {
                    if (configFileName == null) {
                        configFileName = "env-config.yaml"; // default fallback
                    }
                    instance = ConfigReader.readEnvConfig(configFileName);
                }
            }
        }
        return instance;
    }
}
