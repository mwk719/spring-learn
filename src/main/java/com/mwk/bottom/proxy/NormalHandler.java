package com.mwk.bottom.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * 
 * @author 闵渭凯
 *
 *         2019年4月17日
 */
public class NormalHandler implements InvocationHandler {

    private Object target;

    public NormalHandler(Object target) {
        this.target = target;
    }

    public NormalHandler() {
        super();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("startTime=" + System.currentTimeMillis());
        method.invoke(target, args);
        System.out.println("endTime=" + System.currentTimeMillis());
        return null;
    }

}
