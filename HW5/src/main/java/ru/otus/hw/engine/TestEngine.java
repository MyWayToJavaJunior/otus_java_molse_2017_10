package ru.otus.hw.engine;

import ru.otus.hw.utils.ReflectionHelper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class TestEngine<T> {
    ;
    private List<TestAgregator> agregators;

    public TestEngine(Class<T> clazz) {
        agregators = Collections.singletonList(ReflectionHelper.getAgregatorFromClass(clazz));
    }

    public TestEngine(String packageName) throws IOException {
        agregators = ReflectionHelper.getAgregatorsFromPackage(packageName);
    }

    public void runTests() {
        agregators.forEach(agregator -> {

            agregator.getListTest().forEach(testMethod -> {
                System.out.println("-------------  Запуска теста "+((Method) testMethod).getName()+ "--------------------");
                Object instantiate = ReflectionHelper.instantiate(agregator.getType());

                agregator.getListBefore().forEach(beforeMethod ->{
                    ReflectionHelper.callMethod(instantiate, ((Method) beforeMethod).getName());
                });

                try {
                    ReflectionHelper.callMethod(instantiate, ((Method) testMethod).getName());
                    System.out.format("Mетод %s успешно выполнился\n", ((Method) testMethod).getName());
                } catch (RuntimeException e) {
                    System.out.format("!!!!!!!!!!!!!!! Mетод %s не работает !!!!!!!!!!!!!!!!!!1\n", ((Method) testMethod).getName());
                }

                agregator.getListAfter().forEach(afterMethod ->{
                    ReflectionHelper.callMethod(instantiate, ((Method) afterMethod).getName());
                });

            });

        } );
    }
}
