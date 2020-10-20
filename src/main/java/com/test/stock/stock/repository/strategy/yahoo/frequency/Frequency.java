package com.test.stock.stock.repository.strategy.yahoo.frequency;

import com.test.stock.stock.repository.strategy.yahoo.Period;

/**
 * Created by koseungbin on 2020-10-19
 */

public interface Frequency {
	Long getSpareDays(Period period);

	Long getTailoredStartDateAccordingToFrequency(Period period);

	String getSymbol();
}
