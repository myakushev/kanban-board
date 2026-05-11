package com.myakushev.api.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBCleaner {

    public static void cleanKanbanSchema(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            cleanKanbanSchema(conn);
        }
    }

    public static void cleanKanbanSchema(Connection conn) throws SQLException {
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try (Statement st = conn.createStatement()) {
            st.execute("TRUNCATE TABLE public.task, public.kanban RESTART IDENTITY CASCADE;");

            // st.execute(\"ALTER SEQUENCE IF EXISTS public.kanban_id_sequence RESTART WITH 1;\");
            // st.execute(\"ALTER SEQUENCE IF EXISTS public.task_id_sequence RESTART WITH 1;\");

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(prevAutoCommit);
        }
    }
}

