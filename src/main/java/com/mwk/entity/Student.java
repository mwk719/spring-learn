package com.mwk.entity;

import com.ibiz.excel.picture.support.model.BizExcelPojoInterface;
import com.mwk.function.sort.AbstractComparableMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends AbstractComparableMap<Student> implements BizExcelPojoInterface {

	public Student() {
	}

	public Student(String name, Schoolbag bag) {
		this.name = name;
		this.bag = bag;
	}

	public Student(String name, Date date, Schoolbag bag) {
		this.name = name;
		this.date = date;
		this.bag = bag;
	}

	public Student(String name, List<Subject> subjects) {
		this.name = name;
		this.subjects = subjects;
	}

	public Student(String name, Integer age, String headPicture, List<String> album, Integer performance) {
		this.name = name;
		this.age = age;
		this.headPicture = headPicture;
		this.album = album;
		this.performance = performance;
	}

	private String name;

	private Date date;

	private String identity;

	private Schoolbag bag;

	private List<Subject> subjects;

	private Integer age;

	private String headPicture;

	/**
	 * 相册
	 */
	private List<String> album;

	/**
	 * 表现 0一般；1良好；2优秀
	 */
	private Integer performance;

	@Override
	public void buildCompareMap(Student o) {
		map1 = this.getSubjects().stream().collect(Collectors.toMap(Subject::getName, Subject::getValue));
		map2 = o.getSubjects().stream().collect(Collectors.toMap(Subject::getName, Subject::getValue));
	}
}
