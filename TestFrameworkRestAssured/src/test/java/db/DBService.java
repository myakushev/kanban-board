package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class DBService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/kanban";
    private static final String DB_USER = "kanban";
    private static final String DB_PASS = "kanban";

    private static final List<String> TABLES_ORDER = Arrays.asList(
            "kanban",
            "task"  // сначала tasks, т.к. ссылается на kanban_boards
    );

    public static void cleanDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET session_replication_role = 'replica';");
                for (String table : TABLES_ORDER) {
                    stmt.executeUpdate("DELETE FROM " + table);
                    stmt.executeUpdate("ALTER SEQUENCE " + table + "_id_seq RESTART WITH 1;");
                }
                stmt.execute("SET session_replication_role = 'origin';");
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }
}
