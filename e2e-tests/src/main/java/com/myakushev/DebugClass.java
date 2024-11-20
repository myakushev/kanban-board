package com.myakushev;

import com.myakushev.config.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DebugClass {

    @Autowired
    EnvConfig envConfig;

    @Autowired
    ApplicationContext applicationContext;

    public void message() {
        System.out.println("=======================================");
        System.out.println(envConfig.getJdbc().get("backend").getDatabaseName());
        System.out.println(envConfig.getWebGatewayUrl());
        System.out.println("=======================================");
    }
}
