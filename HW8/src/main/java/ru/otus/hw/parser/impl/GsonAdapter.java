package ru.otus.hw.parser.impl;

import com.google.gson.Gson;
import ru.otus.hw.parser.JsonParser;

public class GsonAdapter implements JsonParser {

    private Gson gson = new Gson();
    @Override
    public <T> String toJson(T t) {
        return gson.toJson(t);
    }
}
