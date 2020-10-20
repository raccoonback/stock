package com.test.stock.stock.repository.strategy.yahoo.frequency;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by koseungbin on 2020-10-19
 */

@AllArgsConstructor
public abstract class AbsFrequency implements Frequency {
	protected static final Long STUCK = 0L;

	@Getter
	private String symbol;

}
