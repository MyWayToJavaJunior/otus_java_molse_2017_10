package ru.otus.hw;

import ru.otus.hw.test.TestRunner;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class Main {

    public static void main(final String... args) throws InterruptedException, IOException {

        checkSingleClass();
        checkPackage();
    }

    private static void checkPackage() throws IOException {
        List<TestAgregator> agregators = ReflectionHelper.getAgregatorList("ru.otus.hw.test");
        runTests(agregators);
    }

    private static void checkSingleClass() {
        List<TestAgregator> agregators = ReflectionHelper.getAgregatorList(TestRunner.class);
        runTests(agregators);
    }

    private static void runTests(List<TestAgregator> agregators) {
        agregators.forEach(agregator -> {

            agregator.getListTest().forEach(testMethod -> {
                System.out.println("-------------  Запуска теста "+((Method) testMethod).getName()+ "--------------------");
                Object instantiate = ReflectionHelper.instantiate(agregator.getType());

                agregator.getListBefore().forEach(beforeMethod ->{
                    ReflectionHelper.callMethod(instantiate, ((Method) beforeMethod).getName());
                });

                ReflectionHelper.callMethod(instantiate, ((Method) testMethod).getName());

                agregator.getListAfter().forEach(afterMethod ->{
                    ReflectionHelper.callMethod(instantiate, ((Method) afterMethod).getName());
                });

            });

        } );
    }

}
