package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;
import java.util.List;

public class GenericStreamsTest {

	static Employee[] arrayOfEmployees;
	static List<Employee> listOfEmployees;

	@BeforeAll
	public static void initializeDataStructures() {
		arrayOfEmployees = new Employee[] {
				new Employee(1, "Arya Stark", 150000.0),
				new Employee(2, "John Snow", 200000.0),
				new Employee(2, "Cersei Lannister", 300000.0)
		};
		listOfEmployees = Arrays.asList(arrayOfEmployees);
	}
}
