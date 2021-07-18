package com.mwk.utils;

import com.mwk.entity.PencilCase;
import com.mwk.entity.Schoolbag;
import com.mwk.entity.Student;
import org.springframework.util.StringUtils;

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
	 * 				"但是只装了{%bag.pencilCase.num%}支{%bag.pencilCase.name%}，因为他是装笔！";
	 * @return
	 */
	public static String beanFormatStr(Object source, String format) {
		String[] str = format.split("\\{%");
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			if (StringUtils.isEmpty(s)) {
				continue;
			}
			int a = s.indexOf("%}");
			String key = s.substring(0, s.indexOf("%}"));
			sb.append(MyOptional.getVStrByField(source, key));
			sb.append(s.substring(a + 2));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Student student = new Student("张三",
				new Date(),
				new Schoolbag("黄色",
						new PencilCase("中性笔", 2)));

		String format = "{%name%}在{%date%}背着{%bag.color%}的书包，" +
				"但是只装了{%bag.pencilCase.num%}支{%bag.pencilCase.name%}，因为他是装笔！";

		System.out.println(beanFormatStr(student, format));

	}
}