package ru.otus.hw.factory;

import ru.otus.hw.parser.JsonParser;
import ru.otus.hw.parser.SimpleJsonAdapter;

public class SimpleJsonFactory extends Factory {

    private static SimpleJsonFactory instance;

    private SimpleJsonFactory(){}

    public static SimpleJsonFactory getInstance() {
        if (instance == null)
            instance = new SimpleJsonFactory();
        return instance;

    }

    @Override
    public JsonParser createParser() {
        return new SimpleJsonAdapter();
    }
}