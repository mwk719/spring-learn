package com.mwk.demo3;

/**
 * 不能准确比较比较浮点数
 * 
 * @author 闵渭凯
 *
 *         2019年4月15日
 */
public class test3 {

    public static void main(String[] args) {
        double x = 2;
        double r = Math.sqrt(x);
        double d = r * r - x;
        System.out.println(d);

        final double epsilon = 1E-14;
        if (Math.abs(d) <= epsilon) {
            System.out.println("等于零");
        }

    }

}
