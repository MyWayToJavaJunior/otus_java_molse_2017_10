package ru.otus.hw.parser;

import org.apache.commons.lang3.ClassUtils;
import org.glassfish.json.JsonParserImpl;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class JavaxJsonAdapter implements JsonParser {

    @Override
    public <T> String toJson(T t) throws IllegalAccessException {

        //boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(aClass);
        if (t.getClass().isArray()){
            JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
            jsonBuilder.add(5);
            jsonBuilder.add(5);
            jsonBuilder.add(5);
            jsonBuilder.add(5);
            jsonBuilder.add(5);
            String result =  jsonBuilder.build().toString();
            // the array got created, add it to the json as a child element
            //jsonBuilder.add("", arr);
            //JsonObject empObj = jsonBuilder.build();
            /*StringWriter strWtr = new StringWriter();
            JsonWriter jsonWtr = Json.createWriter(strWtr);
            jsonWtr.writeObject(arr);
            jsonWtr.close();*/
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
