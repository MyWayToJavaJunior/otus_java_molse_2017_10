package ru.otus.hw;

import ru.otus.hw.factory.JavaxJsonFactory;
import ru.otus.hw.factory.SimpleJsonFactory;
import ru.otus.hw.model.TestingClass.Message;
import ru.otus.hw.parser.JsonParser;

import java.util.Arrays;

public class Main {

    public static void main(final String... args) throws IllegalAccessException {
        JsonParser parser = JavaxJsonFactory.getInstance().createParser();
        int[] arr = new int[5];
        Arrays.fill(arr,5);
        String json = parser.toJson(arr);
    }

}
