package com.mwk.dontknow;

/**
 * 不能准确比较比较浮点数
 * 
 * @author 闵渭凯
 *
 *         2019年4月15日
 */
public class test2 {

    public static void main(String[] args) {
        double x = 2;
        double r = Math.sqrt(x);
        double d = r * r - x;

        System.out.println(d);

    }

}
