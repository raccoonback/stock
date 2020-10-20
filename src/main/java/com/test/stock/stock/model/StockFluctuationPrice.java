package com.test.stock.stock.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-17
 */

@ToString
@Getter
@AllArgsConstructor
public class StockFluctuationPrice {
	private LocalDate date;
	private Price price;

	public boolean validate() {
		return price.validate();
	}
}
