package com.jitendra.demo.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.entity.PersonEntity;

public class PersonConverter {

	public static List<Person> personEntityListToPersonListConverter(List<PersonEntity> personEntityList) {

		return personEntityList.stream().map(PersonConverter::personEntityToPersonConverter)
				.collect(Collectors.toList());

	}

	public static List<PersonEntity> personListToPersonEntityListConverter(List<Person> personList) {

		return personList.stream().map(PersonConverter::personToPersonEntityConverter).collect(Collectors.toList());

	}

	public static Person personEntityToPersonConverter(PersonEntity personEntity) {

		return Person.builder().uid(personEntity.getUid()).firstName(personEntity.getFirstName()).lastName(personEntity.getLastName())
				.age(personEntity.getAge()).build();
	}

	public static PersonEntity personToPersonEntityConverter(Person person) {

		return PersonEntity.builder().uid(person.getUid()).firstName(person.getFirstName()).lastName(person.getLastName())
				.age(person.getAge()).build();
	}

}
