package ru.otus.hw;

import com.google.common.reflect.ClassPath;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
class ReflectionHelper {
    private ReflectionHelper() {
    }

    static <T> T instantiate(Class<T> type, Object... args) {
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

    static <T> List<TestAgregator> getAgregatorList(Class<T> type) {

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
        return new ArrayList<>(Arrays.asList(testAgregator));
    }

    public static List<TestAgregator> getAgregatorList(String packageName) throws IOException {

        List<TestAgregator> agregators = new ArrayList<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath.from(loader).getTopLevelClasses().stream().filter(info -> info.getName().startsWith(packageName)).forEach(info -> {
            final Class<?> clazz = info.load();
            agregators.add(getAgregatorList(clazz).get(0));
        });

        return agregators;
    }


    static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    private static Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
