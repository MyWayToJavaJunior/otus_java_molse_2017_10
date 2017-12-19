package ru.otus.hw.base;

import ru.otus.hw.model.DataSet;
import ru.otus.hw.model.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

    String getMetaData();

    <T extends DataSet> void save(T user);

    <T extends DataSet> T load(long id, Class<T> clazz) throws IllegalAccessException;

    <T extends DataSet> void deleteAll(Class<T> clazz) throws IllegalAccessException;


}
