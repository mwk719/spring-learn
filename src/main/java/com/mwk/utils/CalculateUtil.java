package com.mwk.utils;

import com.mwk.entity.Person;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 数值计算工具
 *
 * @author MinWeikai
 * @date 2021/3/13 10:29
 */
public class CalculateUtil {

    /**
     * 计算集合中数值的合计值，并赋予该新对象
     *
     * @param sources 源集合对象
     * @param voClass 源对象
     * @param <T>
     * @return 已计算合计后的源对象
     */
    public static <T> T getSumValue(List<? extends Object> sources, final Class<T> voClass) {
        Field[] fields = voClass.getDeclaredFields();
        T data = null;
        try {
            data = voClass.newInstance();
            T finalData = data;
            sources.stream().forEach(d -> {
                for (Field field : fields) {
                    if (Objects.equals("serialVersionUID", field.getName())) {
                        continue;
                    }
                    Object oldTemp = null;
                    Object newoldTemp = null;
                    try {
                        oldTemp = ReflectionUtil.invokeGetterMethod(d, field.getName());
                        newoldTemp = ReflectionUtil.invokeGetterMethod(finalData, field.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 此处可以自定义匹配类型，或可以结合业务重写该方法，做一些加减乘除的复杂计算
                    switch (field.getType().getSimpleName()) {
                        case "Integer":
                            oldTemp = (int) Optional.ofNullable(oldTemp).orElse(0) +
                                    (int) Optional.ofNullable(newoldTemp).orElse(0);
                            break;
                        case "Float":
                            oldTemp = (float) Optional.ofNullable(oldTemp).orElse(0f) +
                                    (float) Optional.ofNullable(newoldTemp).orElse(0f);
                            oldTemp = new BigDecimal((float) oldTemp)
                                    .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                            break;
                        case "BigDecimal":
                            oldTemp = new BigDecimal(String.valueOf(Optional.ofNullable(oldTemp).orElse(BigDecimal.ZERO)))
                                    .add(new BigDecimal(String.valueOf(Optional.ofNullable(newoldTemp).orElse(BigDecimal.ZERO))))
                                    .setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        default:
                            oldTemp = null;
                    }
                    if (!Objects.isNull(oldTemp)) {
                        ReflectionUtil.invokeSetterMethod(finalData, field.getName(), oldTemp);
                    }
                }
            });
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
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
        data2.setWeight(11f);
        list.add(data2);

        Person person = CalculateUtil.getSumValue(list, Person.class);
        System.out.println(person);
    }

}
