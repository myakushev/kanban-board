package com.myakushev.api.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public final class DataSources {

    public static DataSource postgresHikari(String host, int port, String db,
                                            String user, String password) {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db
                + "?currentSchema=public";

        Properties props = new Properties();
        props.setProperty("jdbcUrl", url);
        props.setProperty("username", user);
        props.setProperty("password", password);

        props.setProperty("maximumPoolSize", "10");
        props.setProperty("minimumIdle", "1");
        props.setProperty("connectionTimeout", "10000");
        props.setProperty("idleTimeout", "600000");
        props.setProperty("maxLifetime", "1800000");
        props.setProperty("connectionTestQuery", "SELECT 1");

        HikariConfig cfg = new HikariConfig(props);
        return new HikariDataSource(cfg);
    }
}


