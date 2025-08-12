package com.myakushev.utils;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public final class DataSources {

    public static DataSource postgresHikari(String host, int port, String db,
                                            String user, String password) {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db
                + "?currentSchema=public"; // при желании укажите схему

        Properties props = new Properties();
        props.setProperty("jdbcUrl", url);
        props.setProperty("username", user);
        props.setProperty("password", password);

        // Базовые настройки пула
        props.setProperty("maximumPoolSize", "10");          // подберите под нагрузку
        props.setProperty("minimumIdle", "1");
        props.setProperty("connectionTimeout", "10000");     // мс
        props.setProperty("idleTimeout", "600000");          // мс
        props.setProperty("maxLifetime", "1800000");         // мс

        // Для Postgres полезно указать тест запроса (опционально)
        props.setProperty("connectionTestQuery", "SELECT 1");

        // Если нужен SSL (например, в облаках), добавьте в URL: &sslmode=require
        // String url = "...?sslmode=require&currentSchema=public";

        HikariConfig cfg = new HikariConfig(props);
        return new HikariDataSource(cfg);
    }
}

