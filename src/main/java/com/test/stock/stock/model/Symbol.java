package com.test.stock.stock.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by koseungbin on 2020-10-21
 */

@Getter
@RequiredArgsConstructor
public class Symbol {
	@NotBlank
	private final String value;
}
