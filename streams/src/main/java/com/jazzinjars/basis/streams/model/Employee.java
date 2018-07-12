package com.jazzinjars.basis.streams.model;

import lombok.Data;

@Data
public class Employee {

	private Integer id;
	private String name;
	private Double salary;

	public Employee(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}

	public double salaryIncrement(double percentage) {
		this.salary += salary * percentage / 100;
		return this.salary;
	}
}
