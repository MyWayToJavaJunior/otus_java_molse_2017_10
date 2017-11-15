package ru.otus.hw;

import ru.otus.hw.test.TestRunner;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class Main {

    public static void main(final String... args) throws InterruptedException, IOException {

        TestEngine testEngine = new TestEngine(TestRunner.class);
        testEngine.runTests();

        TestEngine testEnginePackage = new TestEngine("ru.otus.hw.test");
        testEnginePackage.runTests();

        //checkPackage();
    }

    /*private static void checkPackage() throws IOException {
        List<TestAgregator> agregators = ReflectionHelper.getAgregatorFromClass("ru.otus.hw.test");
        runTests(agregators);
    }*/



}
