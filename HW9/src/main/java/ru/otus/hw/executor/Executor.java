package ru.otus.hw.executor;

import java.sql.*;
import java.util.Map;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, Map<Integer,Object> params, TResultHandler<T> handler) throws SQLException, IllegalAccessException {
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            params.forEach((index, value) -> {
                try {
                    stmt.setObject(index, value);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public <T> void execUpdate(String update, TExecuteHandler<T> handler) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement(update)) {
            handler.accept(stmt);
            stmt.close();
        }
    }

    Connection getConnection() {
        return connection;
    }

}
