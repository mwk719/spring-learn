package com.mwk.function.killifelse.enume;

/**
 * 枚举方式重构
 *
 * @author MinWeikai
 * @date 2021/2/4 10:12
 */
public class EnumExample {

    public static int calculate(int a, int b, Operator operator) {
        return operator.apply(a, b);
    }
}
