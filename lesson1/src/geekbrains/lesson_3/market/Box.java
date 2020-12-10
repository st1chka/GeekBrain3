package geekbrains.lesson_3.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Box {
    static List<Citrus> orange = new ArrayList<>();
    static ArrayList<Applle> apple = new ArrayList<>();
    public static final float WEIGHT_APPLE = 1.0f;
    public static final float WEIGHT_ORANGE = 1.5f;


    public static float noCitrus() {


        apple.add(new Applle());
        apple.add(new Applle());
        apple.add(new Applle());
        apple.add(new Applle());

        return apple.size() * WEIGHT_APPLE;
    }


    public static float citrus() {

        orange.add(new Orange());
        orange.add(new Orange());


        return orange.size() * WEIGHT_ORANGE;
    }


    public static void getWeight() {
        float weightOrange=citrus();
        float weightApple=noCitrus();


        System.out.println("Вес яблок "+weightApple+"кг");
        System.out.println("Вес апельсинов "+weightOrange+"кг");
    }


    public static boolean compare() {
        return Math.abs(citrus() - (noCitrus())) < 0.0001;
    }


    public static int intersperse() {

        List<Citrus> baskets = new ArrayList<>(orange);

        orange.clear();

        return baskets.size()/2;
    }
}
