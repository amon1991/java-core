package com.amon.javacore.algorithm.sort;

import java.util.Random;

/**
 * 快速排序
 * 原理：
 * 调用partition算法查找p位置，p位置代表p之前的元素都小于p，p之后的元素都大于p，然后不断递归排序p左右两侧的数组元素，直到左右元素有序为止
 * partition算法原理：
 * 第一步：采用随机值交换第一个元素（left）和任一元素的位置（目的，防止递归树的不平衡状态）
 * 第二步：循环要查找p位置的数组范围，p位置从left开始，将所有小于起始left元素值的元素左移交换，并将p位置加1，否则继续循环
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/16.
 */
public class QuickSort {

    /**
     * 循环函数中指定的数组范围，找到p位置
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] arr, int left, int right) {


        // 在指定的数组范围中，将起始位置的元素值与数组范围内的随机下标的元素值交换
        // 这是一步算法优化过程，目的是尽可能的防止递归树的不平衡状态
        int random = new Random().nextInt(right - left + 1) + left;
        int t = arr[left];
        arr[left] = arr[random];
        arr[random] = t;

        // 设v为当前要找到合适位置的元素值
        int v = arr[left];
        // p的起始位置为left
        int p = left;

        // 算法核心：
        // 1、当i位置的元素>v时，i+1；
        // 2、当i位置的元素<v时，将i位置的元素和p+1的位置交换，交换后p++
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] < v) {

                int temp = arr[i];
                arr[i] = arr[p + 1];
                arr[p + 1] = temp;
                p++;

            }
        }

        // 将left位置和p位置进行交换
        arr[left] = arr[p];
        arr[p] = v;

        return p;

    }

    private static void sort(int[] arr, int left, int right) {

        if (left >= right) {
            return;
        } else {

            // 经过partition函数后，数组中p位置已经找到了正确的元素值
            int p = partition(arr, left, right);
            sort(arr, left, p - 1);
            sort(arr, p + 1, right);

        }

    }

    public static void main(String[] args) {

        int[] arr = SortTestHelper.generateRandomArray(10, 10, 100);
        // do sort
        sort(arr, 0, arr.length - 1);
        // print result
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();

    }

}
