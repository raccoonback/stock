package com.test.stock.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 * Created by koseungbin on 2020-10-19
 */

public class DateUtils {
	public static LocalDateTime toLocalDateTime(Long timestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
	}

	public static LocalDate toLocalDate(Long timestamp) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId())
			.toLocalDate();
	}

	public static Long toTimestamp(LocalDateTime localDateTime) {
		return localDateTime
			.toInstant(ZoneOffset.UTC)
			.getEpochSecond();
	}
}
