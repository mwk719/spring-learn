package com.mwk.demo3;

import java.util.Random;

/**
 * 蒙特卡罗方法计算Π
 * 
 * @author 闵渭凯
 *
 *         2019年4月15日
 */
public class MonteCarlo {

    public static void main(String[] args) {
        final int TRIES = 100000000;// 随机次数
        Random generator = new Random();

        int hits = 0;// 命中次数
        for (int i = 0; i < TRIES; i++) {
            double r = generator.nextDouble();// r>=0 && r<1
            double x = -1 + 2 * r;// x坐标，r>=-1 && r<1
            r = generator.nextDouble();// r>=0 && r<1
            double y = -1 + 2 * r;// y坐标，r>=-1 && r<1

            // 检查是否落在单位圆内
            if (x * x + y * y <= 1) {
                hits++;
            }
        }
        // 命中数/尝试数之比大致等于 圆面积/正方形面积之比=pi/4

        double piEstimate = 4.0 * hits / TRIES;

        System.out.println("Π 约等于：" + piEstimate);

    }

}
