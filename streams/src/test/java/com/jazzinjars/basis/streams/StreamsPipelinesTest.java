package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

}
