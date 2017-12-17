package ru.otus.hw.utils;

import ru.otus.hw.model.DataSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatementHelper {

    public static <T extends DataSet> String prepareInsertString(T user) throws IllegalAccessException {

        String result = "INSERT INTO public.\"user\"";
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<Field> declaredFields = getFieldsFromObj(user);
        for (Field field: declaredFields) {
            String fName = field.getName();
            field.setAccessible(true);
            Object value = field.get(user);
            if (fName.toString().equals("id") && user.getId() == 0) continue;
            fields.add(fName);
            if (value instanceof String) values.add("'" +value.toString() +"'");
            else values.add(value.toString());
        }

        return result + "(" + fields.stream().collect(Collectors.joining(",")) + ") values(" +
                values.stream().collect(Collectors.joining(","))+")";
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