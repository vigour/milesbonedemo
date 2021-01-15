package com.milesbone.entity;

import java.util.Date;
import java.util.List;

import com.milesbone.common.entity.BaseEntity;

public class User extends BaseEntity{

	public User() {
	}

	public User(String name, int age, float salary, Date birthday) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.birthday = birthday;
	}
	
	

	public User(String name, int age, float salary, Date birthday, List<String> phones) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.birthday = birthday;
		this.phones = phones;
	}


	private String name;

	private int age;

	private float salary;

	private Date birthday;
	
	private List<String> phones;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String toString() {
		return "User [name=" + name + ", age=" + age + ", salary=" + salary + ", birthday=" + birthday + ", phones="
				+ phones + "]";
	}

}
