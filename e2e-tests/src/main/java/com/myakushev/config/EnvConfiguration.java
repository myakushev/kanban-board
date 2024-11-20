package com.myakushev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfiguration {

    @Bean
    public EnvConfig envConfig() {
        return ConfigReader.readEnvConfig("env-config.yaml");
    }
}
