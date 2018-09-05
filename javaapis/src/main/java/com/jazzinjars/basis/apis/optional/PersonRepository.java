package com.jazzinjars.basis.apis.optional;

public class PersonRepository {

	public String findByNameId(String id) {
		return id == null ? null : "Name";
	}
}
