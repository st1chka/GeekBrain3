package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Road extends Stage {
    public static final int CARS_COUNT = 4;
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
//
//    public static void start(int length) {
//        CyclicBarrier cb = new CyclicBarrier(4);
//        try {
//            cb.await();
//            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void go(Car c) {


            try {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
