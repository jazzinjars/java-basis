package com.jazzinjars.basis.streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamsDemoTest {

    @Test
    void streamCreationWithStreamAndOf() {
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> actual = Arrays.stream(arr);
        Stream<String> expected = Stream.of("a", "b", "c");

        Assertions.assertNotEquals(expected, actual);
    }
}
