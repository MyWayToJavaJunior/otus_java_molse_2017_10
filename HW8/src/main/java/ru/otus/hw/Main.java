package ru.otus.hw;

import ru.otus.hw.factory.SimpleJsonFactory;
import ru.otus.hw.parser.JsonParser;

public class Main {

    public static void main(final String... args) {
        JsonParser parser = SimpleJsonFactory.getInstance().createParser();
        String json = parser.toJson();
    }

}
