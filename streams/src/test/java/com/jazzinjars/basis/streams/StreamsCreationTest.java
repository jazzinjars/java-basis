package com.jazzinjars.basis.streams;

import com.jazzinjars.basis.streams.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Test
    public void whenFindMaxOnIntStream_thenGetMaxInteger() {
        Integer latestEmpId = listOfEmployees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElseThrow(NoSuchElementException::new);

        Assertions.assertEquals(latestEmpId, new Integer(3));

        //OR
        IntStream.of(1, 2, 3);
        IntStream.range(10, 20);

        //This return Stream<Integer> and NOT IntStream
        Stream.of(1, 2, 3);
    }

    @Test
    @DisplayName("generate(): gets called whenever new Stream elements need to be generated. Infinite Streams needs a condition to terminate")
    public void wheGenerateStream_thenGetInfiniteStream() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("iterate(): takes 2 parameters: initial value, called seed, and a function which generates next element using the previous value")
    public void whenIterateStream_thenGetInfiniteStream() {
        Stream<Integer> evenNumStream = Stream.iterate(2, i -> i *2);

        List<Integer> collect = evenNumStream
                .limit(5)
                .collect(Collectors.toList());

        Assertions.assertEquals(collect, Arrays.asList(2, 4, 8, 16, 32));
    }

}
