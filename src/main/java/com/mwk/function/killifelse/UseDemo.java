package com.mwk.function.killifelse;

import com.mwk.function.killifelse.bloated.BloatedExample;
import com.mwk.function.killifelse.command.AddCommand;
import com.mwk.function.killifelse.command.CommandExample;
import com.mwk.function.killifelse.engine.Expression;
import com.mwk.function.killifelse.engine.Result;
import com.mwk.function.killifelse.engine.RuleEngine;
import com.mwk.function.killifelse.enume.EnumExample;
import com.mwk.function.killifelse.enume.Operator;
import com.mwk.function.killifelse.factory.OperatorFactoryExample;

/**
 * 使用示例
 *
 * @author MinWeikai
 * @date 2021/2/4 9:50
 */
public class UseDemo {

    public static void main(String[] args) {
        // 臃肿示例
        System.out.println(BloatedExample.calculate(1, 1, "add"));
        System.out.println(BloatedExample.calculateUsingSwitch(1, 1, "add"));

        // 工厂方式重构，适合不同类型的操作耦合度很低，适合处理复杂的业务对象
        System.out.println(OperatorFactoryExample.calculateUsingFactory(1, 1, "add"));

        // 枚举方式重构, 适合不同类型的操作有些许耦合，适合处理简单业务逻辑
        System.out.println(EnumExample.calculate(1, 1, Operator.ADD));

        // 命令模式重构，适合不同类型的操作耦合度很低，适合处理复杂的业务对象
        System.out.println(CommandExample.calculate(new AddCommand(1, 1)));

        // 规则引擎重构, 感觉这个做起来好复杂，好麻烦，不建议在业务中使用。可能适合一些相似对象的操作。
        Expression expression = new Expression(1, 1, Operator.ADD);
        RuleEngine engine = new RuleEngine();
        Result result = engine.process(expression);
        System.out.println(result.getValue());

    }
}
