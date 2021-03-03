package com.jitendra.demo.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.exception.PersonException;
import com.jitendra.demo.service.PersonService;
import com.jitendra.demo.util.TestData;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

	private PersonController personController_mock;

	@Mock
	private PersonService personService_mock;

	@Before
	public void init() {
		personController_mock = new PersonController(personService_mock);
	}

	@Test
	public void findById_should_pass() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.findById(any())).willReturn(person);

		// when
		Person personResponse = personController_mock.findById(person.getUid());

		// then
		assertEquals(personResponse.getUid(), person.getUid());
		assertEquals(personResponse.getFirstName(), person.getFirstName());
		assertEquals(personResponse.getLastName(), person.getLastName());
		assertEquals(personResponse.getAge(), person.getAge());
	}

	@Test(expected = PersonException.class)
	public void findById_should_fail() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.findById(any())).willThrow(new PersonException());

		// when
		personController_mock.findById(person.getUid());
	}

	@Test
	public void findAll_should_pass() {

		// given
		List<Person> persons = new ArrayList<Person>();
		Person person = TestData.getSamplePerson();
		persons.add(person);
		given(personService_mock.findAll()).willReturn(persons);

		// when
		List<Person> personsResponse = personController_mock.findAll();

		// then
		assertNotNull(personsResponse);
		assertEquals(personsResponse.size(), persons.size());
	}

	@Test(expected = RuntimeException.class)
	public void findAll_should_fail() {

		// given
		given(personService_mock.findAll()).willThrow(new RuntimeException());

		// when
		personController_mock.findAll();
	}

	@Test
	public void deletePerson_should_pass() {

		// given
		Person person = TestData.getSamplePerson();

		// when
		String response = personController_mock.deletePerson(person.getUid());

		// then
		verify(personService_mock, times(1)).deletePerson(person.getUid());
		assertEquals(response, String.format("Person with Id - %s deleted !", person.getUid()));
	}

	@Test(expected = RuntimeException.class)
	public void deletePerson_should_fail() {

		// given
		Person person = TestData.getSamplePerson();
		doThrow(new RuntimeException()).when(personService_mock).deletePerson(person.getUid());

		// when
		personController_mock.deletePerson(person.getUid());
	}

	@Test
	public void updatePerson_should_pass() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.updatePerson(any())).willReturn(person);

		// when
		Person personResponse = personController_mock.updatePerson(person);

		// then
		assertEquals(personResponse.getUid(), person.getUid());
		assertEquals(personResponse.getFirstName(), person.getFirstName());
		assertEquals(personResponse.getLastName(), person.getLastName());
		assertEquals(personResponse.getAge(), person.getAge());
	}

	@Test(expected = PersonException.class)
	public void updatePerson_should_fail() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.updatePerson(any())).willThrow(new PersonException());

		// when
		personController_mock.updatePerson(person);
	}

	@Test
	public void addPerson_should_pass() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.addPerson(any())).willReturn(person);

		// when
		Person personResponse = personController_mock.addPerson(person);

		// then
		assertEquals(personResponse.getUid(), person.getUid());
		assertEquals(personResponse.getFirstName(), person.getFirstName());
		assertEquals(personResponse.getLastName(), person.getLastName());
		assertEquals(personResponse.getAge(), person.getAge());
	}

	@Test(expected = PersonException.class)
	public void addPerson_should_fail() {

		// given
		Person person = TestData.getSamplePerson();
		given(personService_mock.addPerson(any())).willThrow(new PersonException());

		// when
		personController_mock.addPerson(person);
	}

}
