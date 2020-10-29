package com.amon.javacore.algorithm.sort;

/**
 * 插入排序
 * 原理：
 * 外层从前往后循环，先保证前n个元素有序，然后n+1个元素从后往前比较，找到自己合适的位置，并把合适位置之后的元素后移一位
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/15.
 */
public class InsertionSort {


    private static void sort(int[] arr) {

        if (null != arr && arr.length > 0) {

            for (int i = 1, len = arr.length; i < len; i++) {

                int num = arr[i];
                int index = i;
                for (int j = i; j > 0; j--) {
                    if (num < arr[j - 1]) {
                        // move j-1 to j
                        arr[j] = arr[j - 1];
                        index = j - 1;
                    } else {
                        break;
                    }
                }
                arr[index] = num;

            }

        }

    }

    public static void main(String[] args) {

        int[] arr = SortTestHelper.generateRandomArray(10, 10, 100);
        // do sort
        sort(arr);
        // print result
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();

    }


}
