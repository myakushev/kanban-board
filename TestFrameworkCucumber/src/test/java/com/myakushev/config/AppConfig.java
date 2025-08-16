package com.myakushev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Gateways gateways = new Gateways();

    @Data
    public static class Gateways {
        private String restGatewayUrl;
    }
}