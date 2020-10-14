package com.amon.javacore.algorithm.sort;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/14.
 */
public class SelectionSort {

    /**
     * sort function
     *
     * @param arr
     */
    private static void sort(int[] arr) {

        if (null != arr && arr.length > 0) {

            for (int i = 0, len = arr.length - 1; i < arr.length; i++) {

                // search min num in [i,len)
                int minIndex = i;
                for (int j = i + 1, jlen = arr.length; j < jlen; j++) {

                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }

                }

                // swap arr[i] and arr[minIndex]
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;

            }

        }

    }

    public static void main(String[] args) {

        int[] arr = SortTestHelper.generateRandomArray(10,10,100);
        // do sort
        sort(arr);
        // print result
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();

    }


}
