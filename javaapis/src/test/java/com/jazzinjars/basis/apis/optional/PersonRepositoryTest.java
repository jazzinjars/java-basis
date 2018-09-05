package com.jazzinjars.basis.apis.optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PersonRepositoryTest {

	PersonRepository personRepository = new PersonRepository();

	@Test
	public void whenIdIsNull_thenExceptionIsThrown() {
		assertThrows(IllegalAccessException.class,
				() -> Optional.ofNullable(personRepository.findByNameId(null))
								.orElseThrow(IllegalArgumentException::new)
		);
	}

	@Test
	public void whenIdIsNonNull_thenNoExceptionIsThrown() {
		assertAll(
				() -> Optional.ofNullable(personRepository.findByNameId("id"))
								.orElseThrow(RuntimeException::new)
		);
	}

	@Test
	public void whenIdNonNull_thenReturnsNameUpperCase() {
		String name = Optional
							.ofNullable(personRepository.findByNameId("id"))
							.map(String::toUpperCase)
							.orElseThrow(RuntimeException::new);

		assertEquals("NAME", name);
	}
}
