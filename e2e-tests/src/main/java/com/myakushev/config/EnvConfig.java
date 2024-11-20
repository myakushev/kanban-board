package com.myakushev.config;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Holds environment configuration for tests
 */
public class EnvConfig {
    private Gateways gateways;
    private Map<String, Jdbc> jdbc;

    public void setGateways(Gateways gateways) {
        this.gateways = gateways;
    }

    public String getWebGatewayUrl() {
        return gateways.getWebGatewayUrl();
    }

    public Map<String, Jdbc> getJdbc() {
        return jdbc;
    }

    public void setJdbc(Map<String, Jdbc> jdbc) {
        this.jdbc = jdbc;
    }

    public static class Jdbc {
        private String serverName;
        private int portNumber;
        private String databaseName;
        private String user;
        private String password;

        public String getServerName() {
            return serverName;
        }

        public int getPortNumber() {
            return portNumber;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        public void setPortNumber(int portNumber) {
            this.portNumber = portNumber;
        }

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Gateways {
        private String webGatewayUrl;

        public String getWebGatewayUrl() {
            return webGatewayUrl;
        }

        public void setWebGatewayUrl(String webGatewayUrl) {
            this.webGatewayUrl = webGatewayUrl;
        }
    }
}
