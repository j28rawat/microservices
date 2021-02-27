package com.jitendra.demo.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Person {

	@NotEmpty
	private String uid;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotNull
	private Integer age;
}
