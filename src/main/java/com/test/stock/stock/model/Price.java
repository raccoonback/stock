package com.test.stock.stock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-17
 */

@ToString
@Getter
@AllArgsConstructor
public class Price {
	private Money buy;
	private Money sell;

	public boolean validate() {
		return buy.hasBalance() || sell.hasBalance();
	}
}
