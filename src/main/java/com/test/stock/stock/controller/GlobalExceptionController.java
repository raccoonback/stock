package com.test.stock.stock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.stock.exception.IllegalThirdpartyRequestException;
import com.test.stock.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by koseungbin on 2020-10-20
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFoundException(NotFoundException e) {
		log.error("not exist symbol request", e);
		return e.getMessage();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {IllegalThirdpartyRequestException.class, IllegalStateException.class})
	public String handleIllegalException(Exception e) {
		log.error("internal error", e);
		return e.getMessage();
	}
}
