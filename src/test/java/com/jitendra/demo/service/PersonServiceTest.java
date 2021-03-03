package com.jitendra.demo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.entity.PersonEntity;
import com.jitendra.demo.exception.PersonException;
import com.jitendra.demo.repository.PersonRepository;
import com.jitendra.demo.util.TestData;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

	private PersonService personService_mock;

	@Mock
	private PersonRepository personRepository_mock;

	@Before
	public void init() {
		personService_mock = new PersonService(personRepository_mock);
	}

	@Test
	public void findById_should_pass() {

		// given
		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.of(personEntity));

		// when
		Person person = personService_mock.findById(personEntity.getUid());

		// then
		assertEquals(personEntity.getUid(), person.getUid());
		assertEquals(personEntity.getFirstName(), person.getFirstName());
		assertEquals(personEntity.getLastName(), person.getLastName());
		assertEquals(personEntity.getAge(), person.getAge());
	}

	@Test(expected = PersonException.class)
	public void findById_should_fail() {

		// given
		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.empty());

		// when
		personService_mock.findById(personEntity.getUid());
	}

	@Test
	public void findAll_should_pass() {

		// given
		List<PersonEntity> personEntityList = new ArrayList<PersonEntity>();
		personEntityList.add(TestData.getSamplePersonEntity());

		given(personRepository_mock.findAll()).willReturn(personEntityList);

		// when
		List<Person> personList = personService_mock.findAll();

		// then
		assertNotNull(personList);
		assertEquals(personEntityList.size(), personList.size());
	}

	@Test(expected = RuntimeException.class)
	public void findAll_should_fail() {

		// given
		given(personRepository_mock.findAll()).willThrow(new RuntimeException());

		// when
		personService_mock.findAll();
	}

	@Test
	public void deletePerson_should_pass() {

		// given
		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.of(personEntity));

		// when
		personService_mock.deletePerson(personEntity.getUid());

		// then
		verify(personRepository_mock, times(1)).delete(personEntity);
	}

	@Test(expected = PersonException.class)
	public void deletePerson_should_fail() {

		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.empty());

		// when
		personService_mock.deletePerson(personEntity.getUid());
	}

	@Test
	public void addPerson_should_pass() {

		// given
		Person person = TestData.getSamplePerson();
		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.save(any())).willReturn(personEntity);

		// when
		personService_mock.addPerson(person);

		// then
		assertEquals(personEntity.getUid(), person.getUid());
		assertEquals(personEntity.getFirstName(), person.getFirstName());
		assertEquals(personEntity.getLastName(), person.getLastName());
		assertEquals(personEntity.getAge(), person.getAge());
	}

	@Test(expected = RuntimeException.class)
	public void addPerson_should_fail() {

		Person person = TestData.getSamplePerson();
		given(personRepository_mock.save(any())).willThrow(new RuntimeException());

		// when
		personService_mock.addPerson(person);
	}

	@Test
	public void updatePerson_should_pass() {

		// given
		Person person = TestData.getSamplePerson();
		PersonEntity personEntity = TestData.getSamplePersonEntity();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.of(personEntity));
		given(personRepository_mock.save(any())).willReturn(personEntity);

		// when
		personService_mock.updatePerson(person);

		// then
		assertEquals(personEntity.getUid(), person.getUid());
		assertEquals(personEntity.getFirstName(), person.getFirstName());
		assertEquals(personEntity.getLastName(), person.getLastName());
		assertEquals(personEntity.getAge(), person.getAge());
	}

	@Test(expected = PersonException.class)
	public void updatePerson_should_fail() {

		//given
		Person person = TestData.getSamplePerson();
		given(personRepository_mock.findByUid(any())).willReturn(Optional.empty());
		
		// when
		personService_mock.updatePerson(person);
	}
}
