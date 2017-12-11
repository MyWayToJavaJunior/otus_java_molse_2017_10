package ru.otus.hw.parser.impl;

import org.apache.commons.lang3.ClassUtils;
import ru.otus.hw.parser.JsonParser;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class JavaxJsonAdapter implements JsonParser {

    @Override
    public <T> String toJson(T t) throws IllegalAccessException {

        if (getClassType(t) == ClassType.Array || getClassType(t) == ClassType.Collection){
            return writeToString(prepareArray(t).build());
        } else if (getClassType(t) == ClassType.String)
            return t.toString();
        else {
            return writeToString(getJsonObject(t));
        }


    }

    private <T> JsonArrayBuilder prepareArray(T t) throws IllegalAccessException {
        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
        if (ClassUtils.isPrimitiveOrWrapper(t.getClass().getComponentType())){
            makePrimitiveArray(t,jsonBuilder);
        }
        else {
            makeComplexArray(t,jsonBuilder);
        }
        return jsonBuilder;
    }

    private <T> void makeComplexArray(T t, JsonArrayBuilder jsonBuilder) throws IllegalAccessException {
        if (getClassType(t) == ClassType.Collection) {
            for (Object obj : ((Collection<Object>) t)) {
                jsonBuilder.add(getJsonObject(obj));
            }
            return;
        }
        Object[] t1 = (Object[]) t;
        for (Object obj:t1) {
            jsonBuilder.add(getJsonObject(obj));
        }
    }

    private <T>JsonObject getJsonObject(T obj) throws IllegalAccessException {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field: declaredFields) {
            String fName = field.getName();
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value instanceof String) {
                builder.add(fName,value.toString());
            } else
            builder.add(fName,toJson(value));
        }
        return builder.build();
    }


    private <T> void makePrimitiveArray(T t,JsonArrayBuilder jsonArrayBuilder){

        Class<?> componentType = t.getClass().getComponentType();
        if (componentType.equals(Integer.TYPE)) {
            int[] array =  (int[]) t;
            for (int i = 0; i < array.length ; i++) {
                jsonArrayBuilder.add(array[i]);
            }
        } else if (componentType.equals(Float.TYPE)) {
            float[] array =  (float[]) t;
            for (int i = 0; i < array.length ; i++) {
                jsonArrayBuilder.add(array[i]);
            }
        } else if (componentType.equals(Double.TYPE)) {
            double[] array =  (double []) t;
            for (int i = 0; i < array.length ; i++) {
                jsonArrayBuilder.add(array[i]);
            }
        } else if (componentType.equals(Boolean.TYPE)) {
            boolean[] array =  (boolean []) t;
            for (int i = 0; i < array.length ; i++) {
                jsonArrayBuilder.add(array[i]);
            }
        }
    }

    private <T> ClassType getClassType(T t) {
        if (t.getClass().isArray()) return ClassType.Array;
            else if (ClassUtils.isPrimitiveOrWrapper(t.getClass())) return ClassType.Primitive;
                else if (t instanceof Collection) return ClassType.Collection;
                    else if (t instanceof Map) return ClassType.Map;
                        else if (t instanceof String ) return ClassType.String;
        return ClassType.Object;
    }



    private static enum ClassType {
        Primitive, Array, Collection,Map, Object, String
    }


    private static String writeToString(JsonObject jsonst) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeObject(jsonst);
        }

        return stWriter.toString();
    }

    private static String writeToString(JsonArray jsonst) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeArray(jsonst);
        }

        return stWriter.toString();
    }

}
