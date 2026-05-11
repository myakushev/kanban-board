package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties;
    private static Config config;

    private Config() {
        properties = new Properties();
        loadProperties(PROPERTIES_FILE);
    }

    public static String getProperty(String key) {
        getConfig();
        return properties.getProperty(key);
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void loadProperties(String fileName) {
        try(InputStream stream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                System.err.println("File isn't founded " + fileName );
            }
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Error during file reading " + fileName);
            throw new RuntimeException(e);
        }
    }

}
