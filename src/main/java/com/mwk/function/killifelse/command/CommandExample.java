package com.mwk.function.killifelse.command;

/**
 * 命令模式
 *
 * @author MinWeikai
 * @date 2021/2/4 10:20
 */
public class CommandExample {

    public static int calculate(Command command) {
        return command.execute();
    }
}
