package com.jitendra.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jitendra.demo.domain.Person;
import com.jitendra.demo.service.PersonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

	private final PersonService personService;

	@GetMapping("/{uid}")
	public Person findById(@PathVariable String uid) {
		return personService.findById(uid);
	}

	@GetMapping
	public List<Person> findAll() {

		return personService.findAll();
	}

	@DeleteMapping("/{uid}")
	public String deletePerson(@PathVariable String uid) {

		personService.deletePerson(uid);
		return String.format("Person with Id - %s deleted !", uid);
	}

	@PutMapping
	public Person updatePerson(@Valid @RequestBody Person person) {

		return personService.updatePerson(person);
	}

	@PostMapping
	public Person addPerson(@Valid @RequestBody Person person) {

		return personService.addPerson(person);
	}

}
