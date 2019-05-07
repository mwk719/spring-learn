package com.mwk.demo3;

import java.util.Random;

/**
 * 骰子
 * 
 * @author 闵渭凯
 *
 *         2019年4月15日
 */
public class Die {

    private Random generator;

    private int sides;// 面数

    /**
     * 构造指定面数的骰子
     * 
     * @param sid
     */
    public Die(int sid) {
        sides = sid;
        generator = new Random();
    }

    /**
     * 模拟掷色子
     * 
     * @return
     */
    public int cast() {
        return 1 + generator.nextInt(sides);
    }

}
