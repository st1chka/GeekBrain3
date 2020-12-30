package ru.johnnygomezzz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями
 @Test. Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект
 типа Class, или имя класса. Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой
 имеется, далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite.
 К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми будет
 выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения. Методы с аннотациями
 @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, иначе необходимо бросить RuntimeException
 при запуске «тестирования».

 */

public class Main {

    public static int beforeCount = 0;
    public static int afterCount = 0;

    public static void main(String[] args) throws Exception {

        start(Testing.class);
        start(Testing1.class);

    }

    public static void start(Class myClass) throws Exception {
        Method[] methods = myClass.getDeclaredMethods();
        Set<Integer> prioritySet = new HashSet<>();

        checkBeforeAfter(methods);

        executeBefore(methods);

        executeTest(methods, prioritySet);

        executeAfter(methods);
    }

    private static void checkBeforeAfter(Method[] methods) {
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {

                beforeCount++;
                if (beforeCount > 1) {
                    throw new RuntimeException("@BeforeSuite уже присутствует");
                }
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                afterCount++;
                if (afterCount > 1) {
                    throw new RuntimeException("@AfterSuite уже присутствует");
                }
            }
        }
    }

    private static void executeBefore(Method[] methods) throws IllegalAccessException, InvocationTargetException {
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
        }
    }

    private static void executeTest(Method[] methods, Set<Integer> prioritySet) throws Exception {
        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {

                prioritySet.add(m.getAnnotation(Test.class).priority());
            }
        }
        List<Integer> priorityList = new ArrayList<>(prioritySet);
        Collections.sort(priorityList);

        for (int i = 0; i < priorityList.size(); i++) {

            for (Method m : methods) {
                if (m.isAnnotationPresent(Test.class)) {
                    if (priorityList.get(i) == m.getAnnotation(Test.class).priority()) {
                        m.invoke(null);
                    }
                }
            }
        }
    }

    private static void executeAfter(Method[] methods) throws IllegalAccessException, InvocationTargetException {
        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class))
            {
                m.invoke(null);

                afterCount++;
                if (afterCount > 1) {
                    throw new RuntimeException("@AfterSuite уже присутствует");
                }
            }
        }
    }

}
