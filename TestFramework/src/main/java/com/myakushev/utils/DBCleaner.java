package com.myakushev.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBCleaner {

    /**
     * Полностью очищает таблицы public.task и public.kanban
     * и сбрасывает связанные sequences к стартовому значению.
     *
     * Работает в одной транзакции.
     */
    public static void cleanKanbanSchema(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            cleanKanbanSchema(conn);
        }
    }

    /**
     * Та же логика, если удобнее передавать уже открытое соединение.
     */
    public static void cleanKanbanSchema(Connection conn) throws SQLException {
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try (Statement st = conn.createStatement()) {
            // Самый быстрый и безопасный способ:
            //  - очищает обе таблицы
            //  - сбрасывает sequences, связанные с identity/serial колонками (обычно к 1)
            //  - учитывает FK благодаря CASCADE либо одновременному перечислению таблиц
            st.execute("TRUNCATE TABLE public.task, public.kanban RESTART IDENTITY CASCADE;");

            // Дополнительно (не обязательно): можно явно рестартнуть известные sequences.
            // Если имена строго такие, как вы указали, раскомментируйте:
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
