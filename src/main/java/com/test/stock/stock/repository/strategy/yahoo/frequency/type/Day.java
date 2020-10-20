package com.test.stock.stock.repository.strategy.yahoo.frequency.type;

import com.test.stock.stock.repository.strategy.yahoo.Period;
import com.test.stock.stock.repository.strategy.yahoo.frequency.AbsFrequency;

/**
 * Created by koseungbin on 2020-10-19
 */

public class Day extends AbsFrequency {
	public Day() {
		super("1d");
	}

	@Override
	public Long getSpareDays(Period period) {
		return STUCK;

	}

	@Override
	public Long getTailoredStartDateAccordingToFrequency(Period period) {
		return period.getStartTimestamp();
	}
}
