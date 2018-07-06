package com.jazzinjars.basis.streams.repository;

import com.jazzinjars.basis.streams.model.Employee;

import java.util.List;

public class EmployeeRepository {

	private List<Employee> employees;

	public EmployeeRepository(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee findById(int id) {

		for (Employee employee : employees) {
			if (employee.getId() == id)
				return employee;
		}
		return null;
	}

	public Employee getEmployee() {
		return new Employee(0, "a", 0.);
	}
}
