package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BinaryOperator;
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

	@Test
	@DisplayName("groupingBy(): takes a classification function as its parameter and apply it to each element of the Stream")
	public void whenStreamGroupingBy_thenGetMap() {
		Map<Character, List<Employee>> groupByAlphabet = listOfEmployees.stream()
			.collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0))));

		Assertions.assertEquals(groupByAlphabet.get('A').get(0).getName(), "Arya Stark");
		Assertions.assertEquals(groupByAlphabet.get('C').get(0).getName(), "Cersei Lannister");
		Assertions.assertEquals(groupByAlphabet.get('J').get(0).getName(), "John Snow");
	}

	@Test
	@DisplayName("mapping(): group data into a type other tahn the element type adapting Collector to a different type")
	public void whenStreamMapping_thenGetMap() {
		Map<Character, List<Integer>> idGroupedByAlphabet = listOfEmployees.stream()
				.collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0)),
						Collectors.mapping(Employee::getId, Collectors.toList())));

		Assertions.assertEquals(idGroupedByAlphabet.get('A').get(0), new Integer(1));
		Assertions.assertEquals(idGroupedByAlphabet.get('C').get(0), new Integer(3));
		Assertions.assertEquals(idGroupedByAlphabet.get('J').get(0), new Integer(2));
	}

	@Test
	@DisplayName("reducing(): returns a Collector which performs a reduction of its input elements")
	public void whenStreamReducing_thenGetValue() {
		Double percentage = 10.0;
		Double salIncrOverhead = listOfEmployees.stream()
				.collect(Collectors.reducing(0.0, e -> e.getSalary() * percentage / 100, (s1, s2) -> s1 + s2));

		Assertions.assertEquals(salIncrOverhead, 65000.0, 0.1);
	}

	@Test
	@DisplayName("reducing(): is most useful when used in a multi-level reduction, downstream of GroupingBy() or PartitioningBy()")
	public void whenStreamGroupingAndReducing_thenGetMap() {
		Comparator<Employee> byNameLength = Comparator.comparing(Employee::getName);

		Map<Character, Optional<Employee>> longestNameByAlphabet = listOfEmployees.stream()
				.collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0)),
						Collectors.reducing(BinaryOperator.maxBy(byNameLength))));

		Assertions.assertEquals(longestNameByAlphabet.get('A').get().getName(), "Arya Stark");
		Assertions.assertEquals(longestNameByAlphabet.get('C').get().getName(), "Cersei Lannister");
		Assertions.assertEquals(longestNameByAlphabet.get('J').get().getName(), "John Snow");
	}
}
