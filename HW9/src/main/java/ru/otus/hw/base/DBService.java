package ru.otus.hw.base;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {
    String getMetaData();

    List<UsersDataSet> getAllUsers() throws SQLException;
}
