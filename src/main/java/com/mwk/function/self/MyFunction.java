package com.mwk.function.self;

import java.io.Serializable;

/**
 * 我的函数方法接口
 *
 * @author MinWeikai
 * @date 2021-11-26 11:03:07
 */
@FunctionalInterface
public interface MyFunction<T, R> extends java.util.function.Function<T, R>, Serializable {

}