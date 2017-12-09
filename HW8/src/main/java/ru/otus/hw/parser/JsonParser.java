package ru.otus.hw.parser;

public interface JsonParser {
    public abstract <T> String toJson(T t) throws IllegalAccessException;
}
