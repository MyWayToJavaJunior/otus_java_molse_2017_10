package ru.otus.hw.engine;

import ru.otus.hw.exception.AssertRuntimeException;

public class Assert {
    public static void assertTrue(boolean value) {
        if (!value) throw new AssertRuntimeException();
    }
}
