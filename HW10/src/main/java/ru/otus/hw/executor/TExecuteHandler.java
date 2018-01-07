package ru.otus.hw.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface TExecuteHandler<T>  {
    void accept(PreparedStatement statement) throws SQLException;
}
