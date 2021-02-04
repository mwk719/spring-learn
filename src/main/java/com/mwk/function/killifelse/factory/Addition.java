package com.mwk.function.killifelse.factory;

/**
 * 加法实现A
 */
public class Addition implements Operation {
    @Override
    public int apply(int a, int b) {
        return a + b;
    }
}