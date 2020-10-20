package com.test.stock.exception;

/**
 * Created by koseungbin on 2020-10-20
 */

public class NotFoundException extends RuntimeException {

	private final String message;

	public NotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
