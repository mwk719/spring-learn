package com.mwk.function.sort;

import cn.hutool.json.JSONUtil;
import com.mwk.entity.Student;
import com.mwk.entity.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 内联集合排序
 *
 * @author MinWeikai
 * @date 2021/8/14 17:36
 */
public class InlineListSort {


	public static void main(String[] args) {
		List<Student> students = new ArrayList<>();
		students.add(new Student("李四", Arrays.asList(
				new Subject("语文", "0"),
				new Subject("数学", "69"))
		));
		students.add(new Student("王五", Arrays.asList(
				new Subject("语文", "65"),
				new Subject("数学", "78"))
		));
		students.add(new Student("赵六", Arrays.asList(
				new Subject("语文", "25"),
				new Subject("数学", "89"))
		));
		students.add(new Student("燕七", Arrays.asList(
				new Subject("语文", "99"),
				new Subject("数学", "95"))
		));


		AbstractComparableMap.fieldName = "数学";
		AbstractComparableMap.orderType = -1;
		Collections.sort(students);
		System.out.println(JSONUtil.toJsonPrettyStr(students));


	}
}
