package ru.otus.hw;

public class Assert {
    public static void assertTrue(boolean b) {
        if (!b) throw new  RuntimeException();
    }
}
