package com.mwk.function.array;

import java.util.Arrays;

public class arryRemove {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] arry = { 1, 8, 1, 5 };
        System.out.println("删除前：" + Arrays.toString(arry));

        int value = 1;
        int currentSize = arry.length;
        int arryLenght = currentSize;
        // 删除
        for (int i = 0; i < arry.length; i++) {
            // 存在相同
            if (arry[i] == value) {
                currentSize--;
            }
            //
            if (currentSize < arryLenght && i < currentSize) {
                arry[i] = arry[i + 1];
            }

        }
        // 缩容
        if (currentSize < arryLenght) {
            arry = Arrays.copyOf(arry, currentSize);
        }

        System.out.println("删除后：" + Arrays.toString(arry));

    }

}
