package ru.johnnygomezzz;

public class Testing1 {

    @BeforeSuite(description = "Тест 1: start")
    public static void beforeTesting() {
        System.out.println("А.С.Пушкин\n");
    }

    @Test(description = "1", priority = 1)
    public static void test3() {
        System.out.println("Я помню чудное мгновенье");
    }

    @Test(description = "2", priority = 2)
    public static void test2() {
        System.out.println("Передо мной явилась ты.");
    }

    @Test(description = "3", priority = 3)
    public static void test1() {
        System.out.println("Как мимолётное виденье,");
    }

    @Test(description = "4", priority = 4)
    public static void test4() {
        System.out.println("Как гений чистой красоты.");
    }

    @AfterSuite(description = "end")
    public static void afterTesting() {
        System.out.println("\nАй да Пушкин...");
    }
}
