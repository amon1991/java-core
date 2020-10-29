package com.amon.javacore.algorithm.sort;

/**
 * 归并排序
 * 原理：
 * 二分递归，保证左右两侧的数组有序，然后调用merge算法
 * merge算法：
 * 将左右两侧的数组进行合并，排序过程需要借用新数组空间，首先将原左右侧数据都复制到新数组空间，然后通过新数组空间的元素进行比较，再将元素赋值到原数组中
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/15.
 */
public class MergeSort {


    /**
     * 对数组中从left到right位置的元素进行排序
     *
     * @param arr
     * @param left
     * @param mid
     * @param right
     */
    private static void merge(int[] arr, int left, int mid, int right) {

        // 拷贝待排序元素到临时数组（临时数据的下标从0开始)
        int[] tempArr = new int[right - left + 1];

        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = arr[i + left];
        }

        // 通过tempArr更新对arr中从left到right位置的元素进行排序
        // 临时数组中左侧数组开始下标
        int i = 0;
        // 临时数组中，右侧数组开始下标
        int j = mid + 1 - left;

        for (int k = left; k <= right; k++) {

            if (i > mid - left) {

                // 左侧数组排序查找结束，直接用右侧数组赋值
                arr[k] = tempArr[j];
                j++;

            } else if (j > right - left) {

                // 右侧数组查找结束，直接用左侧数组赋值
                arr[k] = tempArr[i];
                i++;

            } else if (tempArr[i] < tempArr[j]) {

                // 左侧值小，使用左侧数组赋值
                arr[k] = tempArr[i];
                i++;

            } else if (tempArr[i] >= tempArr[j]) {

                // 右侧值小，使用右侧数组赋值
                arr[k] = tempArr[j];
                j++;

            }

        }


    }


    private static void sort(int[] arr, int left, int right) {

        if (left < right) {

            int mid = (right + left) / 2;

            // 递归二分排序（数组下标采用闭区间）
            sort(arr, left, mid);
            sort(arr, mid + 1, right);

            // merge(仅当左侧数组最后一个元素大于右侧数组第一个元素时，进行合并操作，否则代表需要排序的数组范围已经有序)
            if (arr[mid] > arr[mid + 1]) {
                merge(arr, left, mid, right);
            }

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
