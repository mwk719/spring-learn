package com.mwk.function.killifelse.bloated;

/**
 * https://zhuanlan.zhihu.com/p/76860576
 * 01 臃肿示例
 *
 * @author MinWeikai
 * @date 2021/2/2 18:16
 */
public class BloatedExample {

    public static int calculate(int a, int b, String operator) {
        int result = Integer.MIN_VALUE;
        if ("add".equals(operator)) {
            result = a + b;
        } else if ("multiply".equals(operator)) {
            result = a * b;
        } else if ("divide".equals(operator)) {
            result = a / b;
        } else if ("subtract".equals(operator)) {
            result = a - b;
        } else if ("modulo".equals(operator)) {
            result = a % b;
        }
        return result;
    }

    public static int calculateUsingSwitch(int a, int b, String operator) {
        int result = 0;
        switch (operator) {
            case "add":
                result = a + b;
                break;
            case "multiply":
                result = a * b;
                break;
            case "divide":
                result = a / b;
                break;
            case "subtract":
                result = a - b;
                break;
            case "modulo":
                result = a % b;
                break;
            default:
                result = Integer.MIN_VALUE;
        }
        return result;
    }

}
