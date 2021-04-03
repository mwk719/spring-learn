package com.mwk.utils.convert;

import cn.hutool.core.util.ObjectUtil;
import com.mwk.annotation.RespVoProperty;
import com.mwk.entity.Person;
import com.mwk.entity.PersonVo;
import com.mwk.utils.ReflectionUtil;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数值集合对象转换为字符集合对象，
 * 可自定义数值保留位数，无值时的替换符、单位等
 *
 * @author MinWeikai
 * @date 2021/4/3 11:20
 */
public class Converts {


	/**
	 * 匹配并格式化属性
	 *
	 * @param source
	 * @param keepDecimal
	 * @param unit
	 * @param replaceStr
	 * @param data
	 * @param field
	 * @param <T>
	 */
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

		// 此处可自定义规则
		switch (field.getType().getSimpleName()) {
			case "Float":
				if (ObjectUtil.isNotNull(oldTemp)) {
					if (keepDecimal == -1) {
						oldTemp = new BigDecimal((float) oldTemp);
					} else {
						oldTemp = new BigDecimal((float) oldTemp)
								.setScale(keepDecimal, BigDecimal.ROUND_HALF_UP);
					}
					oldTemp = String.valueOf(oldTemp).concat(unit);
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
					if (keepDecimal == -1) {
						oldTemp = new BigDecimal(oldTemp.toString());
					} else {
						oldTemp = new BigDecimal(oldTemp.toString())
								.setScale(keepDecimal, BigDecimal.ROUND_HALF_UP);
					}
					oldTemp = String.valueOf(oldTemp).concat(unit);
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


	/**
	 * 转化为响应模型
	 *
	 * @param source  源对象
	 * @param voClass vo类
	 * @param <T>
	 * @return
	 */
	public static <T> T toRespVo(Object source, final Class<T> voClass) {
		T data = null;
		try {
			data = voClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		// 返回模型属性map
		Map<String, Field> voFieldsMap = Arrays.stream(voClass.getDeclaredFields())
				.collect(Collectors.toMap(Field::getName, Function.identity()));
		// 对老属性赋予参数并置返回模型
		for (Field field : source.getClass().getDeclaredFields()) {
			RespVoProperty property = Optional.ofNullable(voFieldsMap.get(field.getName()))
					.map(annotationClass -> annotationClass.getAnnotation(RespVoProperty.class))
					.orElse(null);
			if (Objects.isNull(property)) {
				formatVoField(source, -1, "", "", data, field);
			} else {
				formatVoField(source, property.keepDecimal(), property.unit(), property.replaceStr(), data, field);
			}
		}
		return data;
	}


	/**
	 * 集合转化为响应模型集合
	 *
	 * @param sources 源对象集合
	 * @param voClass vo类
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> toRespVos(List<? extends Object> sources, final Class<T> voClass) {
		List<T> targets = new ArrayList<>();
		if (CollectionUtils.isEmpty(sources)) {
			return targets;
		}
		sources.parallelStream().forEach(source -> targets.add(toRespVo(source, voClass)));
		return targets;
	}


	public static void main(String[] args) {
		List<Person> list = new ArrayList<>();
		Person data1 = new Person();
		data1.setLastName("王");
		data1.setName("李三");
		data1.setAge(14);
		data1.setMoney(BigDecimal.ONE);
		data1.setWeight(15.2f);

		System.out.println("-----------------单对象转化vo----------------");

		System.out.println(toRespVo(data1, PersonVo.class));

		System.out.println("--------------------------------------------");


		System.out.println("-----------------集合对象转化vo----------------");

		list.add(data1);

		Person data2 = new Person();
		data2.setName("wangwu");
		data2.setAge(45);
		data2.setMoney(BigDecimal.valueOf(6.41f));
		data2.setWeight(null);
		list.add(data2);

		Person data3 = new Person();
		data3.setName("张思");
		data3.setAge(null);
		data3.setMoney(BigDecimal.valueOf(25f));
		data3.setWeight(17.689f);
		list.add(data3);

		System.out.println(toRespVos(list, PersonVo.class));

		System.out.println("--------------------------------------------");

		Person child = new Person();
		child.setLastName("李");
		child.setName("子网");
		child.setAge(24);
		System.out.println(child);

	}
}
