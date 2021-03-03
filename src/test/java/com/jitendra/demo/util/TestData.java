package com.jitendra.demo.util;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.entity.PersonEntity;

public class TestData {

	public static PersonEntity getSamplePersonEntity() {

		return PersonEntity.builder().id(1).uid("QWERTY").firstName("ABC").lastName("XYZ").age(10).build();

	}

	public static Person getSamplePerson() {

		return Person.builder().uid("QWERTY").firstName("ABC").lastName("XYZ").age(10).build();

	}

}
