package com.mwk.utils;

import com.mwk.entity.PencilCase;
import com.mwk.entity.Schoolbag;
import com.mwk.entity.Student;

import java.util.Date;

/**
 * 数据格式化工具类
 *
 * @author MinWeikai
 * @date 2021/7/17 16:25
 */
public class MyFormat {

	/**
	 * 通过对format与source对象反射匹配拼接值
	 * 需要匹配的值可在format中写为
	 *
	 * @param source
	 * @param format String format = "{%name%}在{%date%}背着{%bag.color%}的书包，" +
	 *               "但是只装了{%bag.pencilCase.num%}支{%bag.pencilCase.name%}，因为他是装笔！";
	 * @return
	 */
	public static String beanFormatStr(Object source, String format) {
		String[] str = format.split("\\{%");
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			int a = s.indexOf("%}");
			if (a != -1) {
				String key = s.substring(0, a);
				sb.append(MyOptional.getVStrByField(source, key));
				sb.append(s.substring(a + 2));
			}else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Student student = new Student();
		student.setName("张三");
		student.setIdentity("匪徒");
		student.setDate(new Date());

		Schoolbag bag = new Schoolbag();
		bag.setColor("黄色");
		bag.setPencilCase(new PencilCase("中性笔", 2));

		student.setBag(bag);

		String format = "各单位请注意：{%identity%}{%name%}于{%date%}背着{%bag.color%}的包，" +
				"里面装了{%bag.pencilCase.num%}支{%bag.pencilCase.name%}";

		System.out.println(beanFormatStr(student, format));

	}
}