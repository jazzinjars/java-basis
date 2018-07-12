package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ComparisonStreamsOperationsTest extends GenericStreamsTest {

	@Test
	@DisplayName("sorted(): sorts the Stream elements based on the comparator. Short-circuiting will not be applied")
	public void whenSortStream_thenGetSortedStream() {
		List<Employee> employees = listOfEmployees.stream()
				.sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
				.collect(Collectors.toList());

		Assertions.assertEquals(employees.get(0).getName(), "Arya Stark");
		Assertions.assertEquals(employees.get(1).getName(), "Cersei Lannister");
		Assertions.assertEquals(employees.get(2).getName(), "John Snow");
	}

	@Test
	@DisplayName("min() max(): return the min and max element in the Stream based on a Comparator. If not exist, return Optional")
	public void whenFindMin_thenGetMinElementFromStream() {
		Employee firstEmp = listOfEmployees.stream()
				.min((e1, e2) -> e1.getId() - e2.getId())
				.orElseThrow(NoSuchElementException::new);

		Assertions.assertEquals(firstEmp.getId(), new Integer(1));
	}

	@Test
	public void whenFindMax_thenGetMaxElementFromStream() {
		Employee maxSalEmp = listOfEmployees.stream()
				.max(Comparator.comparing(Employee::getSalary))
				.orElseThrow(NoSuchElementException::new);

		Assertions.assertEquals(maxSalEmp.getSalary(), new Double(300000));
	}

	@Test
	@DisplayName("distinct(): doesn't take any argument and returns the distinct elements in the Stream eliminating duplicates")
	public void whenApplyDistinct_thenRemoveDuplicatesFromStream() {
		List<Integer> integerList = Arrays.asList(2, 5, 3, 2, 4, 3);
		List<Integer> distinctIntList = integerList.stream().distinct().collect(Collectors.toList());

		Assertions.assertEquals(distinctIntList, Arrays.asList(2, 5, 3, 4));
	}

	@Test
	@DisplayName("allMatch(): checks if predicate is true for all the elements in the Stream" +
			"anyMatch(): checks if predicate is true for any one element. Short-circuiting is applied" +
			"noneMatch(): checks if there are no elements matching the predicate")
	public void whenApplyMatch_thenReturnBoolean() {
		List<Integer> integerList = Arrays.asList(2, 4, 5, 6, 8);

		boolean allEven = integerList.stream().allMatch(i -> i % 2 == 0);
		boolean anyEven = integerList.stream().anyMatch(i -> i % 2 == 0);
		boolean noneMultipleOfThree = integerList.stream().noneMatch(i -> i % 3 == 0);
	}

}
