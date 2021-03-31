package com.mwk.utils.convert;

import cn.hutool.core.util.ObjectUtil;
import com.mwk.annotation.RespVoProperty;
import com.mwk.entity.Person;
import com.mwk.entity.PersonVo;
import com.mwk.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数值集合对象转换为字符集合对象，
 * 可自定义数值保留位数，无值时的替换符
 *
 * @author MinWeikai
 * @date 2021/3/22 18:40
 */
public class Converts {

    private final static String REPLACE_STR = "-";

//    public static <T> List<T> numToStrVo(List<? extends Object> sources, final Class<T> voClass) {
//        List<T> targets = new ArrayList<>();
//        if (CollectionUtils.isEmpty(sources)) {
//            return targets;
//        }
//        Field[] fields = sources.get(0).getClass().getDeclaredFields();
//        sources.stream().forEach(source -> targets.add(formatVoFields(source, voClass, fields)));
//        return targets;
//    }


    public static <T> T numToStrVo(Object source, final Class<T> voClass, int keepDecimal, String unit,
                                   String replaceStr) {
        Field[] fields = source.getClass().getDeclaredFields();
        return formatVoFields(source, voClass, fields, keepDecimal, unit, replaceStr);
    }


    private static <T> T formatVoFields(Object source, Class<T> voClass, Field[] fields, int keepDecimal, String unit,
                                        String replaceStr) {
        T data = null;
        try {
            data = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field field : fields) {
            formatVoField(source, keepDecimal, unit, replaceStr, data, field);
        }
        return data;
    }

    private static <T> void formatVoField(Object source, int keepDecimal, String unit, String replaceStr, T data, Field field) {
        if (Objects.equals("serialVersionUID", field.getName())) {
            return;
        }
        Object oldTemp = null;
        try {
            oldTemp = ReflectionUtil.invokeGetterMethod(source, field.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (field.getType().getSimpleName()) {
            case "Float":
                if (ObjectUtil.isNotNull(oldTemp)) {
                    oldTemp = new BigDecimal((float) oldTemp)
                            .setScale(keepDecimal, BigDecimal.ROUND_HALF_UP).toString().concat(unit);
                } else {
                    oldTemp = replaceStr;
                }
                break;
            case "Integer":
                if (ObjectUtil.isNotNull(oldTemp)) {
                    oldTemp = String.valueOf(oldTemp).concat(unit);
                } else {
                    oldTemp = replaceStr;
                }
                break;
            case "BigDecimal":
                if (ObjectUtil.isNotNull(oldTemp)) {
                    oldTemp = new BigDecimal(oldTemp.toString())
                            .setScale(keepDecimal, BigDecimal.ROUND_HALF_UP).toString().concat(unit);
                } else {
                    oldTemp = replaceStr;
                }
                break;
            default:
        }
        if (ObjectUtil.isNotNull(oldTemp)) {
            ReflectionUtil.invokeSetterMethod(data, field.getName(), oldTemp);
        }
    }


    public static <T> T toRespVo(Object source, final Class<T> voClass) {
        T data = null;
        try {
            data = voClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field field : voClass.getDeclaredFields()) {
            RespVoProperty property = field.getAnnotation(RespVoProperty.class);
            if (Objects.isNull(property)) {
                formatVoField(source, 0, "", "", data, field);
            } else {
                formatVoField(source, property.keepDecimal(), property.unit(), "", data, field);
            }
        }
        return data;
    }


    public static void main(String[] args) {
        List<Person> list = new ArrayList<>();
        Person data1 = new Person();
        data1.setAge(14);
        data1.setMoney(BigDecimal.ONE);
        data1.setWeight(15.2f);
        list.add(data1);

        Person data2 = new Person();
        data2.setAge(1);
        data2.setMoney(BigDecimal.valueOf(1.4f));
        data2.setWeight(null);
        list.add(data2);
//        List<PersonVo> personVos = numToStrVo(list, PersonVo.class);
//        System.out.println(personVos);

        System.out.println(toRespVo(data1, PersonVo.class));


        String a = BigDecimal.valueOf(13.44f).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        System.out.println(a);
    }
}
