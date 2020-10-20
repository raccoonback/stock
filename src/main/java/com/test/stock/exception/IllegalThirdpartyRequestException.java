package com.test.stock.exception;

/**
 * Created by koseungbin on 2020-10-20
 */

public class IllegalThirdpartyRequestException extends RuntimeException {

	private final String message;

	public IllegalThirdpartyRequestException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return String.format("%s does not exist.", message);
	}
}
