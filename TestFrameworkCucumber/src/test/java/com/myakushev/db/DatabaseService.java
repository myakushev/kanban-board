package com.myakushev.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// УБРАЛИ @Service! Теперь это обычный, легковесный класс.
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private final JdbcTemplate jdbcTemplate;
    private final String schemaName;

    // Конструктор остается таким же, его будет вызывать наша Фабрика
    public DatabaseService(JdbcTemplate jdbcTemplate, String schemaName) {
        this.jdbcTemplate = jdbcTemplate;
        this.schemaName = schemaName;
    }

    public List<Map<String, Object>> selectAllFromTable(String tableName) {
        if (!tableName.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid table name format.");
        }
        String qualifiedTableName = "\"" + schemaName + "\".\"" + tableName + "\"";
        return jdbcTemplate.queryForList("SELECT * FROM " + qualifiedTableName);
    }

    public void cleanDatabase() {
        logger.info("Starting database cleanup for schema '{}'...", schemaName);

        List<String> tableNames = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = ?",
                String.class,
                schemaName);

        if (tableNames.isEmpty()) {
            logger.info("No tables found in schema '{}'. Nothing to clean.", schemaName);
            return;
        }

        String tablesToTruncate = tableNames.stream()
                .map(name -> "\"" + schemaName + "\".\"" + name + "\"")
                .collect(Collectors.joining(", "));

        String sql = "TRUNCATE TABLE " + tablesToTruncate + " RESTART IDENTITY CASCADE";
        logger.info("Executing cleanup command: {}", sql);
        jdbcTemplate.execute(sql);
        logger.info("Database schema '{}' cleaned successfully.", schemaName);
    }
}