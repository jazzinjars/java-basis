package com.jazzinjars.basis.streams;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class FileStreamsOperationsTest extends GenericStreamsTest {

	private static final String FILENAME = "file.txt";

	@Test
	public void whenStreamToFile_thenGetFile() throws IOException {
		String[] words = {
			"hello",
			"refer",
			"world",
			"level"
		};

		try (PrintWriter printWriter = new PrintWriter(
				Files.newBufferedWriter(Paths.get(FILENAME)))) {
			Stream.of(words).forEach(printWriter::println);
		}
	}

	@Test
	public void whenFileToStream_thenGetStream() throws IOException {
		List<String> str = getPalindrome(Files.lines(Paths.get(FILENAME)), 5);

		assertThat(str, contains("refer", "level"));
	}

	private List<String> getPalindrome(Stream<String> stream, int length) {
		return stream.filter(s -> s.length() == length)
				.filter(s -> s.compareToIgnoreCase(
					new StringBuilder(s).reverse().toString()) == 0)
				.collect(Collectors.toList());
	}
}
