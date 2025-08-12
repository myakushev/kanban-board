package com.myakushev.config;

import lombok.Data;

import java.util.Map;

/**
 * Holds environment configuration for tests
 */
@Data
public final class EnvConfig {

    private Gateways gateways;
    private Map<String, Jdbc> jdbc;

    @Data
    public static class Gateways {
        private String restGatewayUrl;
    }

    @Data
    public static class Jdbc {
        private String serverName;
        private int portNumber;
        private String databaseName;
        private String schemaName;
        private String user;
        private String password;
    }
}