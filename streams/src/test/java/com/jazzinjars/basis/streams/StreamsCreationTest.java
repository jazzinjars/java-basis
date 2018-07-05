package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamsCreationTest extends GenericStreamsTest {

    @Test
    public void streamCreationFromArrayAndList() {
        Stream<Employee> actual = Stream.of(arrayOfEmployees);
        Stream<Employee> expected = listOfEmployees.stream();

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void streamCreationFromBuilder() {
        Stream<Employee> actual = Stream.of(arrayOfEmployees[0], arrayOfEmployees[1], arrayOfEmployees[2]);

        Stream.Builder<Employee> employeeBuilder = Stream.builder();
        employeeBuilder.accept(arrayOfEmployees[0]);
        employeeBuilder.accept(arrayOfEmployees[1]);
        employeeBuilder.accept(arrayOfEmployees[2]);
        Stream<Employee> expected = employeeBuilder.build();

        Assertions.assertNotEquals(expected, actual);
    }

}
