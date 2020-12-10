package geekbrains.lesson_3;

import geekbrains.lesson_3.market.Box;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {


    public static void main(String[] args) {




        Box.getWeight();

        System.out.println(Box.compare());

        System.out.println("Пересыпали "+Box.intersperse()+" фрукта(ов)");
        //-----------------------------------------------------
        //Первое задание
        Main.swapArray();
        //-----------------------------------------------------
        //Второе задание
        Object [] obj = {"Вася", "Петя"};
        Main.arrayList(obj);
    }



    static void swapArray() {

        String[] arr = new String[5];
        arr[0] = "1";
        arr[1] = "2";
        arr[2] = "3";
        arr[3] = "4";
        arr[4] = "5";

        Object swap = arr[4];
        arr[4] = arr[2];
        arr[2] = (String) swap;
        for (String s : arr) {
            System.out.println(s);
        }
    }


    public static <E>ArrayList<E> arrayList( E [] array){
        return new ArrayList<>(Arrays.asList(array));
    }


}
