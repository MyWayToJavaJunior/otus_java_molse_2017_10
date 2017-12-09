package ru.otus.hw.factory;

import ru.otus.hw.parser.GsonAdapter;
import ru.otus.hw.parser.JsonParser;
import ru.otus.hw.parser.SimpleJsonAdapter;

public class GsonFactory extends Factory {

    private static GsonFactory instance;

    private GsonFactory(){}

    public static GsonFactory getInstance() {
        if (instance == null)
            instance = new GsonFactory();
        return instance;

    }

    @Override
    public JsonParser createParser() {
        return new GsonAdapter();
    }
}
