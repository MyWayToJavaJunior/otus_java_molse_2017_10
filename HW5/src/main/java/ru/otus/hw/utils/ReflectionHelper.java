package ru.otus.hw.utils;

import com.google.common.reflect.ClassPath;
import ru.otus.hw.engine.TestAgregator;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;
import ru.otus.hw.exception.AssertRuntimeException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getDeclaredConstructor(toClasses(args)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> TestAgregator getAgregatorFromClass(Class<T> type) {

        List<Method> methods = Arrays.asList(type.getDeclaredMethods());
        TestAgregator testAgregator = new TestAgregator();
        testAgregator.setType(type);

        List<Method> listBefore = new ArrayList<>();
        List<Method> listTest = new ArrayList<>();
        List<Method> listAfter = new ArrayList<>();

        methods.forEach(method -> {
            if (method.isAnnotationPresent(Before.class)) listBefore.add(method);
            if (method.isAnnotationPresent(Test.class)) listTest.add(method);
            if (method.isAnnotationPresent(After.class)) listAfter.add(method);
        });

        testAgregator.setListBefore(listBefore);
        testAgregator.setListTest(listTest);
        testAgregator.setListAfter(listAfter);
        return testAgregator;
    }

    public static List<TestAgregator> getAgregatorsFromPackage(String packageName) throws IOException {

        List<TestAgregator> agregators = new ArrayList<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath.from(loader).getTopLevelClasses().stream().filter(info -> info.getName().startsWith(packageName)).forEach(info -> {
            final Class<?> clazz = info.load();
            agregators.add(getAgregatorFromClass(clazz));
        });

        return agregators;
    }


    public static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | AssertRuntimeException e) {
                throw new RuntimeException(e);
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
    }

    private static Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
