package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class StreamsPipelinesTest extends GenericStreamsTest {

	@Test
	@DisplayName("Stream Pipeline consists of a Stream Source, 0-n Intermediate Operations and a Terminal Operation")
	public void whenStreamCount_thenGetElementCount() {
		Long empCount = listOfEmployees.stream()
				.filter(e -> e.getSalary() > 200000)
				.count();

		Assertions.assertEquals(empCount, new Long(1));
	}

	@Test
	public void whenLimitInfiniteStream_thenGetFiniteElements() {
		Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

		List<Integer> collect = infiniteStream
				.skip(3)
				.limit(5)
				.collect(Collectors.toList());

		Assertions.assertEquals(collect, Arrays.asList(16, 32, 64, 128, 256));
	}

	@Test
	@DisplayName("Computation is only performed whe the terminal operation is initiated and source elements are consumed as needed" +
			"All Intermediate Operations are lazy, they aren't executed until a result of a processing is actually needed")
	public void whenFindFirst_thenGetFirstEmployeeInStream() {
		Integer[] empIds = {1, 2, 3, 4};

		Employee employee = Stream.of(empIds)
				.map(employeeRepository::findById)
				.filter(e -> e != null)
				.filter(e -> e.getSalary() > 100000)
				.findFirst()
				.orElse(null);

		Assertions.assertEquals(employee.getSalary(), new Double(150000));
	}

	@Test
	@DisplayName("parallel(): perform Stream operations in parallel. Be aware of [thread-safe, order operations, parallel code execution]")
	public void whenParallelStream_thenPerformOperationsInParallel() {
		List<Employee> employeeList = Arrays.asList(arrayOfEmployees);

		employeeList.stream().parallel().forEach(e -> e.salaryIncrement(10.0));

		assertThat(listOfEmployees, contains(
				hasProperty("salary", equalTo(165000.0)),
				hasProperty("salary", equalTo(220000.0)),
				hasProperty("salary", equalTo(330000.0))
		));
	}

}
