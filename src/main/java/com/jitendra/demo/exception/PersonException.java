package com.jitendra.demo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonException extends RuntimeException {

	private static final long serialVersionUID = -5863062217993534887L;

	public PersonException(String message) {

		super(message);
	}

}
