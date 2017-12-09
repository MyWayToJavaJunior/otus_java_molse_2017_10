package ru.otus.hw.parser;

import com.google.gson.Gson;

public class GsonAdapter implements JsonParser {

    private Gson gson = new Gson();
    @Override
    public <T> String toJson(T t) {
        return gson.toJson(t);
    }
}
