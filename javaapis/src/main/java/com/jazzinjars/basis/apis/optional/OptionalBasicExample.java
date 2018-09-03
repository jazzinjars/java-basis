package com.jazzinjars.basis.apis.optional;

import java.util.Optional;

public class OptionalBasicExample {

	public static void main(String[] args) {
//		optionalBasicExample();
		isPresentOptionalAPI();
		createEmptyOptionalObject();
		ifPresentOptionalAPI();
		orElseOptionalAPI();
		orElseGetOptionalAPI();
		orElseThrowOptionalAPI();
		getOptionalAPI();
	}

	private static void optionalBasicExample() {
		Optional<String> gender = Optional.of("MALE");
		String answer1 = "Yes";
		String answer2 = null;

		System.out.println("Non-Empty Optional:" + gender);
		System.out.println("Non-Empty Optional: Gender value : " + gender.get());
		System.out.println("Empty Optional: " + Optional.empty());

		System.out.println("ofNullable on Non-Empty Optional: " + Optional.ofNullable(answer1));
		System.out.println("ofNullable on Empty Optional: " + Optional.ofNullable(answer2));

		// java.lang.NullPointerException
		System.out.println("ofNullable on Non-Empty Optional: " + Optional.of(answer2));
		System.out.println("----");
	}

	// Returns an Optional with the specified present non-null value
	private static void isPresentOptionalAPI() {
		Optional<String> optional = Optional.of("jazzinjars");
		System.out.println(optional.isPresent());
	}

	// Returns an Optional with the specified present non-null value
	private static void createEmptyOptionalObject() {
		Optional<String> emptyOptional = Optional.empty();
		System.out.println(emptyOptional.isPresent());

		// Optional Object with the static of API
		String name = "jazzinjars";
		Optional.of(name);
	}

	// If a value is present, invoke the specified consumer with the value, otherwise do nothing
	private static void ifPresentOptionalAPI() {
		String name = "jazzinjars";
		if (name != null) {
			System.out.println(name.length());
		}
		Optional<String> opt = Optional.of("jazzinjars");
		opt.ifPresent(str -> System.out.println(str.length()));
	}

	// If a value is present, invoke the specified consumer with the value, otherwise do nothing
	private static void orElseOptionalAPI() {
		String nullName = null;
		String name = Optional.ofNullable(nullName).orElse("jazzinjars");
		System.out.println(name);
	}

	// Return the value if present, otherwise invoke other and return the result of that invocation
	private static void orElseGetOptionalAPI() {
		String nullName = null;
		String name = Optional.ofNullable(nullName).orElseGet(() -> "jazzinjars");
		System.out.println(name);
	}

	// Return the contained value, if present, otherwise throw an exception to be created by the provided supplier
	private static void orElseThrowOptionalAPI() {
		String nullName = null;
		String name = Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
		System.out.println(name);
	}

	// If a value is present in this Optional, returns the value, otherwise throws NoSuchElementException
	private static void getOptionalAPI() {
		Optional<String> opt = Optional.of("jazzinjars");
		String name = opt.get();
		System.out.println(name);
	}
}
