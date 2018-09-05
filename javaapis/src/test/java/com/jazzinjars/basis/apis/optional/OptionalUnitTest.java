package com.jazzinjars.basis.apis.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OptionalUnitTest {

	@Test
	public void whenCreatesEmptyOption_thenCorrect() {
		Optional<String> empty = Optional.empty();
		assertFalse(empty.isPresent());
	}

	@Test
	public void givenNonNull_whenCreatesNonNullable_thenCorrect() {
		String name = "jazzinjars";
		Optional<String> opt = Optional.of(name);
		assertEquals("Optional[jazzinjars]", opt.toString());
	}

	@Test
	public void givenNonNull_whenThrowsErrorOnCreate_thenCorrect() {
		String name = null;
		assertThrows(NullPointerException.class,
				() -> Optional.of(name)
		);
	}

	@Test
	public void givenNonNull_whenCreateNullable_thenCorrect() {
		String name = "jazzinjars";
		Optional<String> opt = Optional.ofNullable(name);
		assertEquals("Optional[jazzinjars]", opt.toString());
	}

	@Test
	public void givenNull_whenCreateNullable_thenCorrect() {
		String name = null;
		Optional<String> opt = Optional.ofNullable(name);
		assertEquals("Optional.empty", opt.toString());
	}
}
