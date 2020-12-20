package com.company;

public class Main {
    private static final Object object = new Object();
    public static int calc = 1;
    static final int num = 5;

    public static void main(String[] args) {

///

        new Thread(() -> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (object) {
                        while (calc != 1) {
                            object.wait();
                        }
                        System.out.print("A");
                        calc = 2;
                        object.notifyAll();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }).start();

        //----------------------------------------------------------------------------
        new Thread(() -> {

            try {
                for (int i = 0; i < num; i++) {
                    synchronized (object) {
                        while (calc != 2) {
                            object.wait();
                        }
                        System.out.print("B");
                        calc = 3;
                        object.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        //----------------------------------------------------------------------------
        new Thread(() -> {

            try {
                for (int i = 0; i < num; i++) {
                    synchronized (object) {
                        while (calc != 3) {
                            object.wait();
                        }
                        System.out.print("C");
                        calc = 1;
                        object.notifyAll();
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }


}
