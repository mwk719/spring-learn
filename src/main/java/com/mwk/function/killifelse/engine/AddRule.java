package com.mwk.function.killifelse.engine;

import com.mwk.function.killifelse.enume.Operator;

/**
 * 实现规则AddRule
 *
 * @author MinWeikai
 * @date 2021-02-04 10:34:28
 */
public class AddRule implements Rule {
    private int result;

    @Override
    public boolean evaluate(Expression expression) {
        boolean evalResult = false;
        if (expression.getOperator() == Operator.ADD) {
            this.result = expression.getX() + expression.getY();
            evalResult = true;
        }
        return evalResult;
    }

    @Override
    public Result getResult() {
        return new Result(result);
    }
}