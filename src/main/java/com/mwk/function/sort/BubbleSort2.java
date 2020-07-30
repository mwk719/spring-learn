package com.mwk.function.sort;

import java.util.Arrays;

/*
 * 冒泡排序
 */
public class BubbleSort2 {
    public static void main(String[] args) {

        int[] x = { 1, 4, 2, 6, 8, 5 };
        System.out.println("未排序数组：" + Arrays.toString(x));

        // 外循环次数
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length - i - 1; j++) {
                if (x[j] > x[j + 1]) {
                    int temp = x[j];
                    x[j] = x[j + 1];
                    x[j + 1] = temp;
                }
            }
        }
        System.out.println("冒泡排序：" + Arrays.toString(x));

    }
}
