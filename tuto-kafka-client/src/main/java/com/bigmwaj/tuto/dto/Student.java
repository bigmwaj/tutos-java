package com.bigmwaj.tuto.dto;

public class Student {

	private int age;

	private String name;

	public Student(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}
	
	public Student() {
		super();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
