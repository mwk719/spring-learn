package com.mwk.bottom.proxy;

import java.lang.reflect.Proxy;

public class test {

    public static void main(String[] args) {
        Man man = new Man();
        NormalHandler normalHandler = new NormalHandler(man);

        IPerson person = (IPerson) Proxy.newProxyInstance(Man.class.getClassLoader(),
                man.getClass().getInterfaces(), normalHandler);
        person.say();
    }

}
