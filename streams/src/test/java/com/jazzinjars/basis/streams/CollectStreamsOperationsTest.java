package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CollectStreamsOperationsTest extends GenericStreamsTest {

	@Test
	@DisplayName("joining(): will insert the delimiter between the two String elements of the Stream")
	public void whenCollectByJoining_thenGetJoinedString() {
		String empNames = listOfEmployees.stream()
				.map(Employee::getName)
				.collect(Collectors.joining(", "));

		Assertions.assertEquals(empNames, "Arya Stark, John Snow, Cersei Lannister");
	}

	@Test
	@DisplayName("toSet(): get a set out os Stream elements")
	public void whenCollectBySet_thenGetSet() {
		Set<String> empNames = listOfEmployees.stream()
				.map(Employee::getName)
				.collect(Collectors.toSet());

		Assertions.assertEquals(empNames.size(), 3);
	}

	@Test
	@DisplayName("toCollection(): to extract the elements into any other collection passing a Supplier<Collection>")
	public void whenToVectorCollection_thenGetVector() {
		Vector<String> empNames = listOfEmployees.stream()
				.map(Employee::getName)
				.collect(Collectors.toCollection(Vector::new));

		Assertions.assertEquals(empNames.size(), 3);
	}

	@Test
	@DisplayName("summarizingDouble(): applies a double-producing mapping function to each input element and returns Statistical Class")
	public void whenApplySummarizing_thenGetBasicStats() {
		DoubleSummaryStatistics stats = listOfEmployees.stream()
				.collect(Collectors.summarizingDouble(Employee::getSalary));

		Assertions.assertEquals(stats.getCount(), 3);
		Assertions.assertEquals(stats.getSum(), 650000.0);
		Assertions.assertEquals(stats.getMin(), 150000.0);
		Assertions.assertEquals(stats.getMax(), 300000.0);
		Assertions.assertEquals(stats.getAverage(), 216666.66, 1);
	}

	@Test
	@DisplayName("summaryStatistics(): applies a double-producing mapping function to each input element and returns Statistical Class")
	public void whenApplySummaryStatistics_thenGetBasicStats() {
		DoubleSummaryStatistics stats = listOfEmployees.stream()
				.mapToDouble(Employee::getSalary)
				.summaryStatistics();

		Assertions.assertEquals(stats.getCount(), 3);
		Assertions.assertEquals(stats.getSum(), 650000.0);
		Assertions.assertEquals(stats.getMin(), 150000.0);
		Assertions.assertEquals(stats.getMax(), 300000.0);
		Assertions.assertEquals(stats.getAverage(), 216666.66, 1);
	}

	@Test
	@DisplayName("partitioningBy(): we can partition a Stream into two-based on whether the elements satisfy certain criteria")
	public void whenStreamPartition_thenGetMap() {
		List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
		Map<Boolean, List<Integer>> isEven = intList.stream()
				.collect(Collectors.partitioningBy(i -> i % 2 == 0));

		Assertions.assertEquals(isEven.get(true).size(), 4);
		Assertions.assertEquals(isEven.get(false).size(), 1);
	}
}
