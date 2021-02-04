package com.mwk.function.killifelse.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则引擎
 *
 * @author MinWeikai
 * @date 2021-02-04 10:33:25
 */
public class RuleEngine {
    private static List<Rule> rules = new ArrayList<>();

    static {
        rules.add(new AddRule());
    }

    public Result process(Expression expression) {
        Rule rule = rules.stream()
                .filter(r -> r.evaluate(expression))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expression does not matches any Rule"));
        return rule.getResult();
    }
}