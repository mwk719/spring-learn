package com.mwk.utils;

import com.mwk.entity.PersonDTO;
import com.mwk.function.self.MyFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * 函数类工具
 *
 * @author MinWeikai
 * @date 2021/11/26 11:01
 */
public class FunctionUtil {

    /**
     * 解析方法名
     *
     * @param myFun 好处是属性名修改时可以被感知到
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> String resolveMethodName(MyFunction<T, ?> myFun) throws Exception {
        Method writeReplace = myFun.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        Object sl = writeReplace.invoke(myFun);
        SerializedLambda serializedLambda = (SerializedLambda) sl;
        return serializedLambda.getImplMethodName();
    }

    public static void main(String[] args) throws Exception {
        String name = FunctionUtil.resolveMethodName(PersonDTO::getName);
        System.out.println("解析出的属性名：" + name);
    }
}
