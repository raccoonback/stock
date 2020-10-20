package com.test.stock.stock.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-17
 */

@Getter
@ToString
public class Money {
	private final double value;

	public Money(double value) {
		if (isValid(value)) {
			throw new IllegalArgumentException("유효하지 않은 값입니다.");
		}

		this.value = value;
	}

	private boolean isValid(double value) {
		return value < 0.0;
	}

	public boolean hasBalance() {
		return value > 0.0;
	}

	public Money subtract(Money money) {
		if (value < money.value) {
			throw new IllegalStateException("불가능한 거래입니다.");
		}

		return new Money(value - money.value);
	}
}
