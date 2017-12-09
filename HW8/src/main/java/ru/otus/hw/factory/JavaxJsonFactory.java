package ru.otus.hw.factory;

import ru.otus.hw.parser.JavaxJsonAdapter;
import ru.otus.hw.parser.JsonParser;

public class JavaxJsonFactory extends Factory {

    private static JavaxJsonFactory instance;

    private JavaxJsonFactory(){}

    public static JavaxJsonFactory getInstance() {
        if (instance == null)
            instance = new JavaxJsonFactory();
        return instance;

    }

    @Override
    public JsonParser createParser() {
        return new JavaxJsonAdapter();
    }
}
