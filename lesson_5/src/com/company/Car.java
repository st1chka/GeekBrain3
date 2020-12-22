package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static AtomicInteger ai;
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    static {
        ai = new AtomicInteger(0);
    }
    private Race race;
    private int speed;
    private CyclicBarrier cb;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }
    @Override
    public void run() {
//        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT+1);
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cb.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            if (ai.incrementAndGet()==1){
                System.out.println(name+ " победил!");
            }
            cb.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}