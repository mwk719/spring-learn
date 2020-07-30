package com.mwk.function.array;

import java.util.Arrays;

/**
 * 数组中删除某个元素
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry8 {

    public static void main(String[] args) {
        int LENGTH = 10;
        double[] values = new double[LENGTH];
        for (int i = 0; i < values.length; i++) {
            values[i] = i * i;
        }
        System.out.println(Arrays.toString(values));
        // values=Arrays.copyOf(values, 9);
        // System.out.println(Arrays.toString(values));

        double deleteValue = 16;
        int currentSize = values.length;
        int temp = currentSize;
        for (int i = 0; i < values.length; i++) {
            // 值相同时
            if (values[i] == deleteValue) {
                temp = currentSize - 1;
            }
            // 不是最后一个元素
            if (temp < currentSize && i < temp) {
                values[i] = values[i + 1];
            }
        }
        //System.out.println(Arrays.toString(values));
        // 缩容
        values = Arrays.copyOf(values, temp);
        System.out.println(Arrays.toString(values));

    }

}
