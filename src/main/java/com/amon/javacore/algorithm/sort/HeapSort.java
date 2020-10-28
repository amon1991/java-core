package com.amon.javacore.algorithm.sort;

import com.amon.javacore.algorithm.heap.Heap;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/28.
 */
public class HeapSort {

    private static void sort(int[] arr){


        Heap heap = new Heap(arr.length);

        for (int i : arr) {
            heap.insert(i);
        }

        for (int i = arr.length-1; i >= 0; i--) {
            arr[i] = heap.getMaxItem();
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
