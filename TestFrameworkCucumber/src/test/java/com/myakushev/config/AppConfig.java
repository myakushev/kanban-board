package com.myakushev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Map<String, ServiceProperties> services;

    public ServiceProperties getServiceProperties(String serviceName) {
        ServiceProperties properties = services.get(serviceName);
        if (properties == null) {
            throw new IllegalArgumentException("Configuration for service '" + serviceName + "' not found in application.yml");
        }
        return properties;
    }

    @Data
    public static class ServiceProperties {
        private String gateway;
        private DatabaseProperties db;
    }

    @Data
    public static class DatabaseProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private String schema;
    }
}