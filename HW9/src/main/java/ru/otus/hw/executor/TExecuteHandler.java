package ru.otus.hw.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@FunctionalInterface
public interface TExecuteHandler<T> {
    void accept(Statement statement) throws SQLException;
}
