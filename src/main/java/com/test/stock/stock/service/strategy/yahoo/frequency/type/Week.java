package com.test.stock.stock.service.strategy.yahoo.frequency.type;

import com.test.stock.stock.service.strategy.yahoo.Period;
import com.test.stock.stock.service.strategy.yahoo.frequency.AbsFrequency;

/**
 * Created by koseungbin on 2020-10-19
 */

public class Week extends AbsFrequency {
	public Week() {
		super("1wk");
	}

	@Override
	public Long getSpareDays(Period period) {
		if (period.isStartDateSameAsMonday()) {
			return STUCK;
		}

		return period.countUntilNextMonth();
	}

	@Override
	public Long getTailoredStartDateAccordingToFrequency(Period period) {
		if (period.isStartDateSameAsMonday()) {
			return period.getStartTimestamp();
		}

		return period.getNextMonthTimestamp();
	}
}
