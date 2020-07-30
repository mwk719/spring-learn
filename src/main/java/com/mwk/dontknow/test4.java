package com.mwk.dontknow;

/**
 * 京东小金库收益计算
 * 
 * @author 闵渭凯
 *
 *         2019年4月15日
 */
public class test4 {

    public static void main(String[] args) {
        double yield_ratio = 0.7446 / 10000;
        double balance = 15036.44;
        int day = 0;
        double increase = 0;

        while (day < 7) {
            increase = balance * yield_ratio;
            balance = balance + increase;
            day++;
            System.out.println("第" + day + "天，总金额=" + balance + "，收益=" + increase);
        }

        System.out.println("年数=" + day / 365);

    }

}
