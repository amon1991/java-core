package com.amon.javacore.algorithm.heap;

import java.util.Random;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/10/28.
 */
public class Heap {

    private int[] itemArr;

    /**
     * max size in heap
     */
    private int maxSize;
    /**
     * current length in heap
     */
    private int currentLength;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        currentLength = 0;
        // limit maxsize
        itemArr = new int[maxSize];

    }

    public void insert(int item) {

        if (currentLength + 1 > maxSize) {
            return;
        }

        itemArr[currentLength] = item;
        // do shiftup
        shiftUp(currentLength++);

    }

    private void shiftUp(int index) {

        if (index == 0) {
            return;
        }

        while (index - 1 >= 0) {

            int sonItem = itemArr[index];
            int fatherItem = itemArr[(index - 1) / 2];

            if (sonItem > fatherItem) {
                // do swap
                itemArr[index] = fatherItem;
                itemArr[(index - 1) / 2] = sonItem;

                // update index
                index = (index - 1) / 2;

            } else {
                // finish shiftUp
                return;
            }

        }

    }

    public void printHeap() {

        for (int i = 0; i < currentLength; i++) {
            System.out.print(itemArr[i] + " ");
        }

    }


    public int getMaxItem() {


        int maxItem = itemArr[0];

        // move last item to first
        itemArr[0] = itemArr[currentLength - 1];
        itemArr[currentLength - 1] = 0;
        currentLength--;

        if (currentLength >= 0) {
            // do shift down
            shiftDown(0);
        }

        return maxItem;

    }

    private void shiftDown(int index) {

        if (index >= currentLength - 1) {
            return;
        }

        while (index * 2 + 1 <= currentLength - 1) {

            int fatherItem = itemArr[index];
            int leftSonItem = itemArr[index * 2 + 1];

            if (index * 2 + 2 <= currentLength - 1) {

                int rightSonItem = itemArr[index * 2 + 2];
                if (leftSonItem > fatherItem || rightSonItem > fatherItem) {

                    if (leftSonItem > rightSonItem) {
                        itemArr[index * 2 + 1] = fatherItem;
                        itemArr[index] = leftSonItem;
                        index = index * 2 + 1;
                    } else {
                        itemArr[index * 2 + 2] = fatherItem;
                        itemArr[index] = rightSonItem;
                        index = index * 2 + 2;
                    }

                } else {
                    return;
                }

            } else {

                if (leftSonItem > fatherItem) {
                    itemArr[index * 2 + 1] = fatherItem;
                    itemArr[index] = leftSonItem;
                    index = index * 2 + 1;
                } else {
                    return;
                }

            }

        }

    }

    public static void main(String[] args) {

        Heap heap = new Heap(10);
        Random random = new Random();

        // insert items
        for (int i = 0; i < 10; i++) {
            heap.insert(random.nextInt(100));
        }

        // print items in heap
        heap.printHeap();
        System.out.println();

        for (int i = 0; i < 10; i++) {
            System.out.print(heap.getMaxItem() + " ");
        }

    }


}
