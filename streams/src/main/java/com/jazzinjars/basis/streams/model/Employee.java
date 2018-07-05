package com.jazzinjars.basis.streams.model;

import lombok.Data;

@Data
public class Employee {

	private int id;
	private String name;
	private double salary;

	public Employee(int id, String name, double salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
}
