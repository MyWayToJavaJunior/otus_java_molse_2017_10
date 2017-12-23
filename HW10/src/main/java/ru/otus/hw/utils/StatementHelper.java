package ru.otus.hw.utils;

import ru.otus.hw.model.DataSet;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class StatementHelper {

    public static <T extends DataSet> Map<String,Object> prepareParametersMap(T user) throws IllegalAccessException {

        Map<String,Object> fieldValuesMap = new HashMap<>();
        List<Field> declaredFields = getFieldsFromObj(user);
        for (Field field: declaredFields) {
            String fName = field.getName();
            field.setAccessible(true);
            Object value = field.get(user);
            if (fName.toString().equals("id") && user.getId() == 0) continue;
            fieldValuesMap.put(fName,value);
        }
        return fieldValuesMap;
    }

    public static <T extends DataSet> String getTableName(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) throw new RuntimeException("Данный класс не поддерживается");
        Table annotation = clazz.getAnnotation(Table.class);
        return annotation.name();
    }

    public static <T> List<Field> getFieldsFromObj(T t) {
       return getFieldsFromClass(t.getClass());
    }

    public static List<Field> getFieldsFromClass(Class clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}