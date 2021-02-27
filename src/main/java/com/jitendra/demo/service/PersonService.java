package com.jitendra.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jitendra.demo.converter.PersonConverter;
import com.jitendra.demo.domain.Person;
import com.jitendra.demo.entity.PersonEntity;
import com.jitendra.demo.exception.PersonException;
import com.jitendra.demo.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

	private final PersonRepository personRepository;

	private static final String MISSING_PERSON = "Person with UID : %s does not exists !";

	public Person findById(String uId) {

		Optional<PersonEntity> optionalPersonEntity = personRepository.findByUid(uId);
		return PersonConverter.personEntityToPersonConverter(
				optionalPersonEntity.orElseThrow(() -> new PersonException(String.format(MISSING_PERSON, uId))));
	}

	public List<Person> findAll() {

		return PersonConverter.personEntityListToPersonListConverter(personRepository.findAll());
	}

	@Transactional
	public void deletePerson(String uId) {

		Optional<PersonEntity> optionalPersonEntity = personRepository.findByUid(uId);
		PersonEntity personEntity = optionalPersonEntity
				.orElseThrow(() -> new PersonException(String.format(MISSING_PERSON, uId)));
		personRepository.delete(personEntity);
	}

	@Transactional
	public Person addPerson(Person person) {

		PersonEntity personEntity = PersonConverter.personToPersonEntityConverter(person);
		return PersonConverter.personEntityToPersonConverter(personRepository.save(personEntity));
	}

	@Transactional
	public Person updatePerson(Person person) {

		Optional<PersonEntity> optionalPersonEntity = personRepository.findByUid(person.getUid());
		PersonEntity oldPersonEntity = optionalPersonEntity
				.orElseThrow(() -> new PersonException(String.format(MISSING_PERSON, person.getUid())));
		PersonEntity newPersonEntity = PersonConverter.personToPersonEntityConverter(person);
		newPersonEntity.setId(oldPersonEntity.getId());
		return PersonConverter.personEntityToPersonConverter(personRepository.save(newPersonEntity));
	}

}
