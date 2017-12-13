package ru.otus.hw.parser.impl;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import ru.otus.hw.parser.JsonParser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class JavaxJsonParser implements JsonParser {

    @Override
    public <T> String toJson(T t) throws IllegalAccessException {
        if (t == null) return "null";
        else if (getClassType(t) == ClassType.Array || getClassType(t) == ClassType.Collection){
            return prepareArray(t).build().toString();
        } else  if (ClassUtils.isPrimitiveOrWrapper(t.getClass()) || t instanceof String){
            return prepatePrimitives(t);
        }
        else {
            return getJsonObject(t).build().toString();
        }

    }

    private <T> String prepatePrimitives(T t) {
        if (t instanceof Number) return String.valueOf((Number) t);
        if (t instanceof String || t instanceof Character) {
            JsonString value = Json.createValue(t.toString());
            return value.toString();
        }
        if (t instanceof Boolean) return ((Boolean) t).toString();
        else throw new RuntimeException("Not accepted yet");
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

    private <T> void makePrimitiveArray(T t,JsonArrayBuilder jsonArrayBuilder){

        Class<?> componentType = t.getClass().getComponentType();
        if (componentType.equals(Integer.TYPE)) {
            for (int i = 0; i < Array.getLength(t) ; i++) {
                jsonArrayBuilder.add((int)Array.get(t,i));
            }
        } else if (componentType.equals(Float.TYPE)) {
            for (int i = 0; i < Array.getLength(t) ; i++) {
                jsonArrayBuilder.add((float)Array.get(t,i));
            }
        } else if (componentType.equals(Double.TYPE)) {
            for (int i = 0; i < Array.getLength(t) ; i++) {
                jsonArrayBuilder.add((double)Array.get(t,i));
            }
        } else if (componentType.equals(Boolean.TYPE)) {
            for (int i = 0; i < Array.getLength(t) ; i++) {
                jsonArrayBuilder.add((boolean)Array.get(t,i));
            }
        }
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

    private <T>JsonObjectBuilder getJsonObject(T obj) throws IllegalAccessException {
        JsonObjectBuilder builder = Json.createObjectBuilder();


        List<Field> declaredFields = getFields(obj);
        for (Field field: declaredFields) {
            String fName = field.getName();
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                builder.add(fName,"null");
            } else if (value instanceof String || value instanceof Character) {
                builder.add(fName,value.toString());
            } else if (value instanceof Boolean) {
                builder.add(fName,(Boolean) value);
            } else if (getClassType(value) == ClassType.Array || getClassType(value) == ClassType.Collection) {
                builder.add(fName,prepareArray(value));
            } else if (value instanceof Number) {
                builder.add(fName, Json.createValue(value.toString()));
            }
            else
                builder.add(fName,getJsonObject(value));
        }
        return builder;
    }

    private <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private <T> ClassType getClassType(T t) {
        if (t.getClass().isArray()) return ClassType.Array;
            else if (ClassUtils.isPrimitiveOrWrapper(t.getClass())) return ClassType.Primitive;
                else if (t instanceof Collection) return ClassType.Collection;
                    else if (t instanceof Map) return ClassType.Map;
                        else if (t instanceof String ) return ClassType.String;
        return ClassType.Object;
    }



    private enum ClassType {
        Primitive, Array, Collection,Map, Object, String
    }

}
