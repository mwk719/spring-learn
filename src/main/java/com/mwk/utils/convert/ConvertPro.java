package com.mwk.utils.convert;

import cn.hutool.core.util.ObjectUtil;
import com.mwk.annotation.RespVoProperty;
import com.mwk.entity.Person;
import com.mwk.entity.PersonVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 升级版，可对父类对象进行值转换
 * 参考 {@link BeanUtils#copyProperties(Object, Object, Class, String...)}
 * 数值集合对象转换为字符集合对象，
 * 可自定义数值保留位数，无值时的替换符、单位等
 *
 * @author MinWeikai
 * @date 2021/4/3 19:05
 */
public class ConvertPro {


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
		if (Objects.isNull(data)) {
			return null;
		}

		// 返回模型属性map
		Map<String, Field> voFieldsMap = Arrays.stream(voClass.getDeclaredFields())
				.collect(Collectors.toMap(Field::getName, Function.identity()));

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(data.getClass());

		// 对老属性赋予参数并置返回模型
		for (PropertyDescriptor targetPd : targetPds) {
			RespVoProperty property = Optional.ofNullable(voFieldsMap.get(targetPd.getName()))
					.map(annotationClass -> annotationClass.getAnnotation(RespVoProperty.class))
					.orElse(null);

			if (Objects.isNull(property)) {
				formatVoField(source, -1, "", "", data, targetPd);
			} else {
				formatVoField(source, property.keepDecimal(), property.unit(), property.replaceStr(), data, targetPd);
			}
		}

		return data;
	}

	private static <T> void formatVoField(Object source, int keepDecimal, String unit, String replaceStr, T data, PropertyDescriptor targetPd) {
		Method writeMethod = targetPd.getWriteMethod();
		if (writeMethod != null) {
			PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
			if (sourcePd != null) {
				Method readMethod = sourcePd.getReadMethod();
				if (readMethod != null) {
					try {
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}

						Object value = readMethod.invoke(source);
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}

						// 此处可自定义规则
						switch (sourcePd.getPropertyType().getSimpleName()) {
							case "Float":
								if (ObjectUtil.isNotNull(value)) {
									if (keepDecimal == -1) {
										value = new BigDecimal((float) value);
									} else {
										value = new BigDecimal((float) value)
												.setScale(keepDecimal, BigDecimal.ROUND_HALF_UP);
									}
									value = String.valueOf(value).concat(unit);
								} else {
									value = replaceStr;
								}
								break;
							case "Integer":
								if (ObjectUtil.isNotNull(value)) {
									value = String.valueOf(value).concat(unit);
								} else {
									value = replaceStr;
								}
								break;
							case "BigDecimal":
								if (ObjectUtil.isNotNull(value)) {
									if (keepDecimal == -1) {
										value = new BigDecimal(value.toString());
									} else {
										value = new BigDecimal(value.toString())
												.setScale(keepDecimal, BigDecimal.ROUND_HALF_UP);
									}
									value = String.valueOf(value).concat(unit);
								} else {
									value = replaceStr;
								}
								break;
							default:
						}

						writeMethod.invoke(data, value);
					} catch (Throwable var15) {
						throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
					}
				}
			}
		}
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
		data2.setLastName("王");
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

	}
}
