package com.test.stock.stock.service.strategy.yahoo.frequency.type;

import com.test.stock.stock.service.strategy.yahoo.Period;
import com.test.stock.stock.service.strategy.yahoo.frequency.AbsFrequency;

/**
 * Created by koseungbin on 2020-10-19
 */

public class Month extends AbsFrequency {
	public Month() {
		super("1mo");
	}

	@Override
	public Long getSpareDays(Period period) {
		return period.countUntilFirstDayOfNextMonth();
	}

	@Override
	public Long getTailoredStartDateAccordingToFrequency(Period period) {
		return period.getFirstDayOfNextMonthTimestamp();
	}
}
