package com.mwk.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwk.entity.Schoolbag;
import com.mwk.entity.Student;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author MinWeikai
 * @date 2021/4/3 12:57
 */
public final class MyOptional {

    public static String getVStrByField(Object source, String filed) {
        return getValueByField(source, filed, String.class);
    }


    /**
     * 获取层级对象的值通过层级属性
     *
     * @param <T>
     * @param source 源对象
     * @param filed  可为层级属性，属性名用小圆点.分隔
     * @param vClass 属性类型
     * @return
     */
    public static <T> T getValueByField(Object source, String filed, final Class<T> vClass) {
        Objects.requireNonNull(filed);
        String[] filedNames = filed.split("\\.");
        Object oldTemp;
        if (filedNames.length == 1) {
            oldTemp = ReflectionUtil.invokeGetterMethodNoThrowException(source, filed);
            oldTemp = convertInstanceofByMyStyle(oldTemp, vClass);
            return new ObjectMapper().convertValue(oldTemp, vClass);
        }
        oldTemp = ReflectionUtil.invokeGetterMethodNoThrowException(source, getFirstStrByPoint(filed));
        if (Objects.isNull(oldTemp)) {
            return null;
        }
        oldTemp = getValueByField(oldTemp, getEndStrByPoint(filed), vClass);
        oldTemp = convertInstanceofByMyStyle(oldTemp, vClass);
        return new ObjectMapper().convertValue(oldTemp, vClass);
    }

    /**
     * 根据vClass对构建返回对象
     * @param object
     * @param vClass
     * @param <T>
     * @return
     */
    private static <T> Object convertInstanceofByMyStyle(Object object, Class<T> vClass) {
        if(Objects.isNull(object)){
            return null;
        }
        try {
        	// 当需要输出String时需要对特定格式数据做转换
            if(vClass.newInstance() instanceof String){
                // 自定义处理
                if(object instanceof Date){
                    return DateUtil.formatDateTime(Convert.toDate(object));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 获取层级对象的值通过层级属性
     * 被Optional修饰，可使用Optional中方法处理结果值
     *
     * @param source
     * @param filed
     * @param vClass
     * @param <T>
     * @return
     */
    public static <T> Optional<T> ofNullable(Object source, String filed, final Class<T> vClass) {
        return Optional.ofNullable(getValueByField(source, filed, vClass));
    }


    private static String getFirstStrByPoint(String str) {
        return str.substring(0, str.indexOf("."));
    }

    private static String getEndStrByPoint(String str) {
        return str.substring(str.indexOf(".") + 1);
    }


    public static void main(String[] args) {
        Student student = new Student("李明",
                new Schoolbag("黄色",
                        null));
//                        new PencilCase("中性笔", 2)));

        System.out.println(getValueByField(student, "name", String.class));
        System.out.println(getValueByField(student, "bag", Schoolbag.class));
        System.out.println(getValueByField(student, "bag", Schoolbag.class).getColor());
        System.out.println(getValueByField(student, "bag.color", String.class));
        System.out.println(getValueByField(student, "bag.pencilCase.num", Integer.class));

        // 随意测试值，找不到应该为null
        System.out.println(getValueByField(student, "ww.ee.num", Integer.class));
        System.out.println(getValueByField(student, "bag.ee.num", Integer.class));
        System.out.println(getValueByField(null, "bag.ee.num", Integer.class));

        System.out.println("-----------被Optional修饰的方法------------");
        System.out.println(ofNullable(student, "bag", Schoolbag.class).map(Schoolbag::getColor).orElse("无色"));
        System.out.println(ofNullable(student, "name", String.class));
        System.out.println(ofNullable(student, "bag", Schoolbag.class).isPresent());
        System.out.println(ofNullable(null, "bag.ee.num", Integer.class).orElse(0));
        System.out.println(ofNullable(null, "bag.ee.num", Integer.class).get());

    }

}
