package ru.otus.hw.service;

import ru.otus.hw.annotations.Entity;
import ru.otus.hw.base.DBService;
import ru.otus.hw.model.DataSet;
import ru.otus.hw.connection.ConnectionHelper;
import ru.otus.hw.executor.Executor;
import ru.otus.hw.utils.ReflectionHelper;
import ru.otus.hw.utils.StatementHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.otus.hw.utils.StatementHelper.getTableName;

public class DBServiceImpl implements DBService{

    private static final String INSERT_INTO ="INSERT INTO public.\"";


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
    public <T extends DataSet> void save(T user) {
        try {
            Executor exec = new Executor(getConnection());
            Map<String,Object> fieldValue =  StatementHelper.prepareParametersMap(user);
            String tableName = getTableName(user.getClass());

            String fields = fieldValue.entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(",","(",")"));

            String questions = fieldValue.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(str -> "?")
                    .collect(Collectors.joining(",", " VALUES (", ")"));

            String s = INSERT_INTO + tableName + "\" " + fields + questions;

            exec.execUpdate(s, statement -> {
                List<Object> values = fieldValue.entrySet()
                        .stream()
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());

                for (int i = 0; i < values.size(); i++) {
                    statement.setObject(i + 1,values.get(i));
                }
                statement.execute();
            });
        } catch (SQLException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        String tableName = getTableName(clazz);
        String selectRequest = "select * from public.\""+tableName+"\" where id = ";
        T user = ReflectionHelper.instantiate(clazz);

        try {
            Executor exec = new Executor(getConnection());
            exec.execQuery(selectRequest + id, result -> {
                result.next();
                if (!result.isLast()) return user;
                user.setId((long)result.getObject("id"));
                List<Field> fieldsFromClass = StatementHelper.getFieldsFromClass(clazz)
                        .stream()
                        .filter(f->!f.getName().equals("id"))
                        .collect(Collectors.toList());

                for (Field field: fieldsFromClass) {
                    ReflectionHelper.setFieldValue(user,field.getName(),result.getObject(field.getName()));
                }
                return user;

        });} catch (SQLException | IllegalAccessException e){
            e.printStackTrace();
        }

        return user.getId() == 0 ? null : user;
    }

    @Override
    public <T extends DataSet> void deleteAll(Class<T> clazz) throws IllegalAccessException {

        String tableName = getTableName(clazz);
        String deleteRequest = "DELETE FROM public.\""+tableName+"\"";
        try {
            Executor exec = new Executor(getConnection());
            exec.execUpdate(deleteRequest, PreparedStatement::execute);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    @Override
    public void close() throws Exception {
        connection.close();
    }

    protected Connection getConnection() {
        return connection;
    }
}
