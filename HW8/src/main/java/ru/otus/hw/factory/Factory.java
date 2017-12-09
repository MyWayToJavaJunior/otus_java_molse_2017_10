package ru.otus.hw.factory;

import ru.otus.hw.parser.JsonParser;

public abstract  class Factory {
    public abstract JsonParser createParser();
}
