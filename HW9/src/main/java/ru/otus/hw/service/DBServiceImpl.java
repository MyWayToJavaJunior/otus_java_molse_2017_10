package ru.otus.hw.service;

import ru.otus.hw.base.DBService;
import ru.otus.hw.base.UsersDataSet;
import ru.otus.hw.connection.ConnectionHelper;
import ru.otus.hw.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DBServiceImpl implements DBService{

    private static final String SELECT_TEST = "select * from test;";

    private final Connection connection;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public List<UsersDataSet> getAllUsers() throws SQLException {
        try {
            Executor exec = new Executor(getConnection());
            getConnection().setAutoCommit(false);
            exec.execQuery(SELECT_TEST, statement -> {
                statement.next();
                int id = statement.getInt("id");
                System.out.println(id);
                return "";
            });
            getConnection().commit();
        } catch (SQLException e){
            getConnection().rollback();
        } finally {
            getConnection().setAutoCommit(true);
        }
        return Collections.emptyList();
    }

    @Override
    public void close() throws Exception {

    }

    protected Connection getConnection() {
        return connection;
    }
}
