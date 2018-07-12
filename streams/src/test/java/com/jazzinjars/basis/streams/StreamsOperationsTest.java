package com.jazzinjars.basis.streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsOperationsTest extends GenericStreamsTest {

    @Test
    @DisplayName("forEach() [TerminalOperation]: loops over the stream elements calling the supplied function for each one")
    public void whenIncrementSalaryForEachEmployee_thenApplyNewSalary() {
        listOfEmployees.stream().forEach( e -> e.salaryIncrement(10.0));

        assertThat(listOfEmployees, contains(
            hasProperty("salary", equalTo(165000.0)),
            hasProperty("salary", equalTo(220000.0)),
            hasProperty("salary", equalTo(330000.0))
        ));
    }

    @Test
    @DisplayName("map(): produces new Stream applying a Function to each element of the original Stream and could be a different type")
    public void whenMapIdToEmployees_thenGetEmployeesStream() {
        Integer[] employeeIDs = {1, 2, 3};

        List<Employee> employees = Stream.of(employeeIDs)
                .map(employeeRepository::findById)
                .collect(Collectors.toList());

        Assertions.assertEquals(employees.size(), employeeIDs.length);
    }

    @Test
    @DisplayName("collect() [TerminalOperation]: performs mutable fold operations on data elements held in the Stream Instance to get data out of the Stream")
    public void whenCollectStreamToList_thenGetList() {
        List<Employee> employees = listOfEmployees.stream().collect(Collectors.toList());

        Assertions.assertEquals(listOfEmployees, employees);
    }

    @Test
    @DisplayName("filter() [IntermediateOperation]: produces a new Stream that contains elements of the original that pass a given test/predicate")
    public void whenFilterEmployees_thenGetFilteredStream() {
        Integer[] employeeIDs = {1, 2, 3, 4};

        List<Employee> employees = Stream.of(employeeIDs)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 230000)
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(arrayOfEmployees[2]), employees);
    }

    @Test
    @DisplayName("findFirst(): returns an Optional for the first entry in the Stream")
    public void whenFindFirst_thenGetFirstEmployeeInStream() {
        Integer[] employeeIDs = {1, 2, 3, 4};

        Employee employee = Stream.of(employeeIDs)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 100000)
                .findFirst()
                .orElse(null);

        Assertions.assertEquals(employee.getSalary(), new Double(150000));
    }

    @Test
    @DisplayName("toArray(): to get an Array out of the Stream")
    public void whenStreamToArray_thenGetArray() {
        Employee[] employees = listOfEmployees.stream().toArray(Employee[]::new);

        assertThat(listOfEmployees.toArray(), equalTo(employees));
    }

    @Test
    @DisplayName("flatMap(): to flatten complex data structure in a Stram (ex. Stream<List<String>>)")
    public void whenFlatMapEmployeesNames_thenGetNameStream() {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Arya", "Stark"),
                Arrays.asList("Robb", "Stark"),
                Arrays.asList("Sansa", "Stark"));

        List<String> namesFlatStream = namesNested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Assertions.assertEquals(namesFlatStream.size(), namesNested.size() * 2);
    }

    @Test
    @DisplayName("peek() [IntermediateOperation]: performs an operation on each element of the Stream returning a new stream which can be used further")
    public void whenIncrementSalaryUsingPeek_thenApplynewSalary() {
        listOfEmployees.stream()
                .peek(e -> e.salaryIncrement(10))
                .peek(System.out::println)
                .collect(Collectors.toList());

        assertThat(listOfEmployees, contains(
                hasProperty("salary", equalTo(165000.0)),
                hasProperty("salary", equalTo(220000.0)),
                hasProperty("salary", equalTo(330000.0))
        ));
    }

    @Test
    @DisplayName("Specialized Streams, like IntStream, provide additional operations to deal with numbers. sum(), average(), range()")
    public void whenApplySumOnIntStream_theGetSum() {
        Double avgSal = listOfEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        Assertions.assertEquals(avgSal, new Double(650000));
    }

}
