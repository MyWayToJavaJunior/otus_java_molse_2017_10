package ru.otus.hw.parser;

import org.apache.commons.lang3.ClassUtils;
import org.glassfish.json.JsonParserImpl;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class JavaxJsonAdapter implements JsonParser {

    @Override
    public <T> String toJson(T t) throws IllegalAccessException {

        if (t.getClass().isArray()){
            JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
            if (ClassUtils.isPrimitiveOrWrapper(t.getClass().getComponentType())){
                if (t.getClass().getComponentType().equals(Integer.TYPE)) {
                    int[] array =  (int[]) t;
                    for (int i = 0; i < array.length ; i++) {
                        jsonBuilder.add(array[i]);
                    }
                }
            }
            else {
                Object[] t1 = (Object[]) t;
                for (Object obj:t1) {
                    JsonObjectBuilder jsonBuilderInner = Json.createObjectBuilder();

                    Class<?> aClass = obj.getClass();
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field field: declaredFields) {
                        String fName = field.getName();
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        jsonBuilderInner.add(fName,value.toString());
                    }
                    JsonObject empObj = jsonBuilderInner.build();
                    jsonBuilder.add(empObj);
                }
            }
            String result =  jsonBuilder.build().toString();

            return result;
        }
        else {
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

            Class<?> aClass = t.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field: declaredFields) {
                String fName = field.getName();
                field.setAccessible(true);
                Object value = field.get(t);
                jsonBuilder.add(fName,value.toString());
            }
            JsonObject empObj = jsonBuilder.build();
            StringWriter strWtr = new StringWriter();
            JsonWriter jsonWtr = Json.createWriter(strWtr);
            jsonWtr.writeObject(empObj);
            jsonWtr.close();

            //System.out.println(strWtr.toString());

            return strWtr.toString();
        }


    }
}
