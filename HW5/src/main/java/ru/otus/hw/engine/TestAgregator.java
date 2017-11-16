package ru.otus.hw.engine;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class TestAgregator<T> {

    private Class<T> type;
    private List<Method> listBefore;
    private List<Method> listTest;
    private List<Method> listAfter;
}
