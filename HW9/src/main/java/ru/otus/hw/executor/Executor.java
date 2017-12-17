package ru.otus.hw.executor;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException, IllegalAccessException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public <T> void execUpdate(String update, TExecuteHandler<T> handler) throws SQLException {
        try(Statement stmt = getConnection().createStatement()) {
            handler.accept(stmt);
            stmt.close();
        }
    }

    Connection getConnection() {
        return connection;
    }

}
