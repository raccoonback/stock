package com.test.stock.stock.service.strategy.yahoo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import com.test.stock.stock.service.strategy.yahoo.frequency.Frequency;
import com.test.stock.stock.service.strategy.yahoo.frequency.type.Day;
import com.test.stock.utils.DateUtils;

import lombok.Getter;

/**
 * Created by koseungbin on 2020-10-19
 */

@Getter
public class Period {
	private LocalDateTime endDate;
	private long days;
	private Frequency frequency;

	public Period(LocalDateTime endDate, long days, Frequency frequency) {
		this.days = days;
		this.endDate = endDate;
		this.frequency = frequency;
	}

	public Long getSpareDays() {
		return frequency.getSpareDays(this);
	}

	public Long getTailoredStartDateAccordingToFrequency() {
		return frequency.getTailoredStartDateAccordingToFrequency(this);
	}

	public Long getEndTimestamp() {
		return DateUtils.toTimestamp(endDate);
	}

	public Long getStartTimestamp() {
		return DateUtils.toTimestamp(endDate.minusDays(days));
	}

	public LocalDateTime getStartDate() {
		return endDate.minusDays(days);
	}

	public LocalDateTime toNextMonday() {
		return getStartDate().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
	}

	public boolean isStartDateSameAsMonday() {
		return getStartDate().getDayOfWeek() == DayOfWeek.MONDAY;
	}

	public Long countUntilNextMonth() {
		return ChronoUnit.DAYS.between(getStartDate(), toNextMonday());
	}

	public Long getNextMonthTimestamp() {
		return DateUtils.toTimestamp(toNextMonday());
	}

	public LocalDateTime toNextMonth() {
		return getStartDate().plusMonths(1);
	}

	public LocalDateTime getFirstDayOfNextMonth() {
		return toNextMonth().with(TemporalAdjusters.firstDayOfMonth());
	}

	public Long countUntilFirstDayOfNextMonth() {
		return ChronoUnit.DAYS.between(getStartDate(), getFirstDayOfNextMonth());
	}

	public Long getFirstDayOfNextMonthTimestamp() {
		return DateUtils.toTimestamp(getFirstDayOfNextMonth());
	}

	public Period asSparePeriod() {
		Long spareDays = getSpareDays();
		LocalDateTime endDate = DateUtils
			.toLocalDateTime(getStartTimestamp())
			.plusDays(spareDays);
		return new Period(endDate, spareDays, new Day());
	}
}
