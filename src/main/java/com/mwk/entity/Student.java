package com.mwk.entity;

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
public class Student extends AbstractComparableMap<Student> {

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

	private String name;

	private Date date;

	private String identity;

	private Schoolbag bag;

	private List<Subject> subjects;

	@Override
	public void buildCompareMap(Student o) {
		this.map1 = this.getSubjects().stream().collect(Collectors.toMap(Subject::getName, Subject::getValue));
		this.map2 = o.getSubjects().stream().collect(Collectors.toMap(Subject::getName, Subject::getValue));
	}
}
