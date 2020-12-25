import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    @Test
    public void test() {

        System.out.println(testSearch(new int[]{1, 4, 1, 4}));
        System.out.println(testSearch(new int[]{1, 1, 1, 1, 1, 1}));
        System.out.println(testSearch(new int[]{4, 4, 4, 4}));
        System.out.println(testSearch(new int[]{1, 4, 4, 1, 1, 4, 3}));
        int[] arr1 = {1, 4, 2, 4, 6, 5, 3};
        int[] arr2 = {1, 4, 2, 4, 6, 8, 3};
        int[] arr3 = {1, 4, 2, 4, 2};
        int[] arr4 = {1, 4, 2, 4, 2, 5, 6, 7};

        System.out.println(Arrays.toString(testArr(arr1)));
        System.out.println(Arrays.toString(testArr(arr2)));
        System.out.println(Arrays.toString(testArr(arr3)));
        System.out.println(Arrays.toString(testArr(arr4)));

    }

    public static int[] testArr(int[] arr) {
//        ArrayList<Integer> arrayList = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 4) {
                count++;
            } else {
                count = 0;
            }
        }

        int[] array = new int[count];

        System.arraycopy(arr, arr.length - count, array, 0, count);
        return array;
    }

    private boolean testSearch(int[] arr) {

        int count1 = 0;
        int count4 = 0;

        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == 1) {
                count1++;
            }

            if (arr[i] == 4) {
                count4++;
            }

        }
        if (count1 + count4 < arr.length) {
            return false;
        }
        if (count4 == arr.length) {
            return false;
        }
        if (count1 == arr.length) {
            return false;
        }


        return true;

    }

}

