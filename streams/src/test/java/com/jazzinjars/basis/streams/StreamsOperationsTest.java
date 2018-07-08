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
import java.util.List;
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
    @DisplayName("collect(): performs mutable fold operations on data elements held in the Stream Instance")
    public void whenCollectStreamToList_thenGetList() {
        List<Employee> employees = listOfEmployees.stream().collect(Collectors.toList());

        Assertions.assertEquals(listOfEmployees, employees);
    }

    @Test
    @DisplayName("filter(): produces a new Stream that contains elements of the original that pass a given test/predicate")
    public void whenFilterEmployees_thenGetFilteredStream() {
        Integer[] employeeIDs = {1, 2, 3, 4};

        List<Employee> employees = Stream.of(employeeIDs)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 230000)
                .collect(Collectors.toList());

        Assertions.assertEquals(Arrays.asList(arrayOfEmployees[2]), employees);
    }

}
