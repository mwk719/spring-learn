package com.mwk.function.killifelse.engine;

/**
 * 抽象规则
 * 与activiti工作流{@link EngineServices}原理一样
 *
 * @author MinWeikai
 * @date 2021-02-04 10:33:43
 */
public interface Rule {
    boolean evaluate(Expression expression);

    Result getResult();
}