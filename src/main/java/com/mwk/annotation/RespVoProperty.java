package com.mwk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 响应体vo属性
 *
 * @author MinWeikai
 * @date 2021/3/25 9:34
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RespVoProperty {

    /**
     * 保留小数位数
     * 默认-1不进行保留
     *
     * @return
     */
    int keepDecimal() default -1;

    /**
     * 是否保留小数位的0
     *
     * @return
     */
    boolean keepDecimalZero() default true;

    /**
     * 单位
     *
     * @return
     */
    String unit() default "";

    /**
     * 替换值
     *
     * @return
     */
    String replaceStr() default "";

    /**
     * 日期格式
     *
     * @return
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";


}
