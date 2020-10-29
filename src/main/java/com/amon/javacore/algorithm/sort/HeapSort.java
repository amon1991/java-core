package com.amon.javacore.algorithm.sort;

import com.amon.javacore.algorithm.heap.Heap;

/**
 * 堆排序
 * 原理：
 * 通过堆结构，先将数组元素插入堆中，然后依次取出放到正确的数组位置上即可（最小堆顺序赋值，最大堆逆序赋值）
 *
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
