package com.jazzinjars.basis.reactivestreams;

import lombok.Data;

import java.time.LocalDate;

@Data
public class News {

	private final String headLine;
	private final LocalDate date;

	public static News create(String headLine) {
		return new News(headLine, LocalDate.now());
	}

	private News(String headLine, LocalDate date) {
		this.headLine = headLine;
		this.date = date;
	}
}
