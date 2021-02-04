package com.mwk.function.killifelse.factory;

/**
 * 工厂方式重构
 *
 * @author MinWeikai
 * @date 2021/2/4 9:48
 */
public class OperatorFactoryExample {

    public static int calculateUsingFactory(int a, int b, String operator) {
        Operation targetOperation = OperatorFactory
                .getOperation(operator)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));
        return targetOperation.apply(a, b);
    }

}
