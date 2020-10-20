package com.test.stock.exception;

/**
 * Created by koseungbin on 2020-10-20
 */

public class NotMeasurableException extends RuntimeException {

	private final String message;

	public NotMeasurableException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
