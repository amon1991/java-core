package com.amon.javacore.algorithm.sort;

import java.util.Random;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/14.
 */
public class SortTestHelper {

    public static int[] generateRandomArray(int length, int fromRange, int toRange) {

        int[] arr = new int[length];

        Random random = new Random();

        for (int i = 0, len = arr.length; i < len; i++) {

            arr[i] = random.nextInt(toRange - fromRange + 1) + fromRange;

        }

        return arr;
    }

}
