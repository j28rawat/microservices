package com.jitendra.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.exception.PersonException;
import com.jitendra.demo.service.PersonService;
import com.jitendra.demo.util.TestData;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private PersonService personService_mock;

	private static final String rootUrl = "/persons";

	@Test
	public void findById_should_pass() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		when(personService_mock.findById(any())).thenReturn(expectedPerson);

		// when
		String url = "http://localhost:" + port + rootUrl + "/" + expectedPerson.getUid();
		ResponseEntity<Person> response = restTemplate.getForEntity(new URL(url).toString(), Person.class);

		// then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Person actualPerson = response.getBody();
		assertEquals(expectedPerson.getFirstName(), actualPerson.getFirstName());
		assertEquals(expectedPerson.getLastName(), actualPerson.getLastName());
		assertEquals(expectedPerson.getUid(), actualPerson.getUid());
		assertEquals(expectedPerson.getAge(), actualPerson.getAge());
	}

	@Test
	public void findById_should_fail() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		given(personService_mock.findById(any())).willThrow(new PersonException());

		// when
		String url = "http://localhost:" + port + rootUrl + "/" + expectedPerson.getUid();
		ResponseEntity<Person> response = restTemplate.getForEntity(new URL(url).toString(), Person.class);

		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void findAll_should_pass() throws Exception {

		// given
		List<Person> expectedPersons = new ArrayList<Person>();
		Person person = TestData.getSamplePerson();
		expectedPersons.add(person);
		given(personService_mock.findAll()).willReturn(expectedPersons);

		// when
		String url = "http://localhost:" + port + rootUrl;
		ResponseEntity<List<Person>> response = restTemplate.exchange(new URL(url).toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Person>>() {
				});

		// then
		assertNotNull(response.getBody());
		List<Person> actualPersons = response.getBody();
		assertEquals(expectedPersons.size(), actualPersons.size());
	}

	@Test
	public void findAll_should_fail() throws Exception {

		// given
		List<Person> expectedPersons = new ArrayList<Person>();
		Person person = TestData.getSamplePerson();
		expectedPersons.add(person);
		String expectedErrorMessage = "Connection interrupted !";
		given(personService_mock.findAll()).willThrow(new PersonException(expectedErrorMessage));

		// when
		String url = "http://localhost:" + port + rootUrl;
		ResponseEntity<Exception> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.GET, null,
				Exception.class);

		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		Exception actualException = actualResponse.getBody();
		assertEquals(expectedErrorMessage, actualException.getMessage());
	}

	@Test
	public void deletePerson_should_pass() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		String expectedDeletionMessage = String.format("Person with Id - %s deleted !", expectedPerson.getUid());

		// when
		String url = "http://localhost:" + port + rootUrl + "/" + expectedPerson.getUid();
		ResponseEntity<String> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE, null,
				String.class);

		// then
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		verify(personService_mock, times(1)).deletePerson(any());
		assertEquals(expectedDeletionMessage, actualResponse.getBody());
	}

	@Test
	public void deletePerson_should_fail() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		String expectedErrorMessage = "Connection interrupted !";
		doThrow(new PersonException(expectedErrorMessage)).when(personService_mock).deletePerson(any());

		// when
		String url = "http://localhost:" + port + rootUrl + "/" + expectedPerson.getUid();
		ResponseEntity<Exception> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.DELETE,
				null, Exception.class);

		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		Exception actualException = actualResponse.getBody();
		assertEquals(expectedErrorMessage, actualException.getMessage());
	}

	@Test
	public void updatePerson_should_pass() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		when(personService_mock.updatePerson(any())).thenReturn(expectedPerson);

		// when
		String url = "http://localhost:" + port + rootUrl;
		HttpEntity<Person> httpEntity = new HttpEntity<Person>(expectedPerson);
		ResponseEntity<Person> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
				httpEntity, Person.class);

		// then
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		Person actualPerson = actualResponse.getBody();
		assertEquals(expectedPerson.getFirstName(), actualPerson.getFirstName());
		assertEquals(expectedPerson.getLastName(), actualPerson.getLastName());
		assertEquals(expectedPerson.getUid(), actualPerson.getUid());
		assertEquals(expectedPerson.getAge(), actualPerson.getAge());
	}

	@Test
	public void updatePerson_should_fail() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		String expectedErrorMessage = "Connection interrupted !";
		doThrow(new PersonException(expectedErrorMessage)).when(personService_mock).updatePerson(any());

		// when
		String url = "http://localhost:" + port + rootUrl;
		HttpEntity<Person> httpEntity = new HttpEntity<Person>(expectedPerson);
		ResponseEntity<Exception> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.PUT,
				httpEntity, Exception.class);

		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		Exception actualException = actualResponse.getBody();
		assertEquals(expectedErrorMessage, actualException.getMessage());
	}

	@Test
	public void addPerson_should_pass() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		when(personService_mock.addPerson(any())).thenReturn(expectedPerson);

		// when
		String url = "http://localhost:" + port + rootUrl;
		HttpEntity<Person> httpEntity = new HttpEntity<Person>(expectedPerson);
		ResponseEntity<Person> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
				httpEntity, Person.class);

		// then
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		Person actualPerson = actualResponse.getBody();
		assertEquals(expectedPerson.getFirstName(), actualPerson.getFirstName());
		assertEquals(expectedPerson.getLastName(), actualPerson.getLastName());
		assertEquals(expectedPerson.getUid(), actualPerson.getUid());
		assertEquals(expectedPerson.getAge(), actualPerson.getAge());
	}

	@Test
	public void addPerson_should_fail() throws Exception {

		// given
		Person expectedPerson = TestData.getSamplePerson();
		String expectedErrorMessage = "Connection interrupted !";
		doThrow(new PersonException(expectedErrorMessage)).when(personService_mock).addPerson(any());

		// when
		String url = "http://localhost:" + port + rootUrl;
		HttpEntity<Person> httpEntity = new HttpEntity<Person>(expectedPerson);
		ResponseEntity<Exception> actualResponse = restTemplate.exchange(new URL(url).toString(), HttpMethod.POST,
				httpEntity, Exception.class);

		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
		Exception actualException = actualResponse.getBody();
		assertEquals(expectedErrorMessage, actualException.getMessage());
	}
}
