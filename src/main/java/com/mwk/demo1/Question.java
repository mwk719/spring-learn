package com.mwk.demo1;

import java.util.ArrayList;

public class Question {

	private ArrayList<String> choices;

	public Question() {
		choices = new ArrayList<>();
	}

	public void add(String option) {
		choices.add(option);
	}

	public int getSize() {
		return choices.size();

	}

	public static void main(String[] args) {
		Question question = new Question();
		question.add("A");
		question.add("B");
		question.add("C");
		System.out.println(question.getSize());

	}

}
