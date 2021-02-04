package com.mwk.function.killifelse.engine;


import com.mwk.function.killifelse.enume.Operator;

/**
 * 表达式
 *
 * @author MinWeikai
 * @date 2021-02-04 10:32:47
 */
public class Expression {
    private Integer x;
    private Integer y;
    private Operator operator;

    public Expression(Integer x, Integer y, Operator operator) {
        this.x = x;
        this.y = y;
        this.operator = operator;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Operator getOperator() {
        return operator;
    }
}