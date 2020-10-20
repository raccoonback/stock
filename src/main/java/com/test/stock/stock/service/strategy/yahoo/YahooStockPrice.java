package com.test.stock.stock.service.strategy.yahoo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import com.test.stock.stock.model.Money;
import com.test.stock.stock.service.strategy.model.StockPrice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-18
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class YahooStockPrice implements StockPrice {
	private Long date;
	private double open;
	private double high;
	private double low;
	private double close;
	private double adjclose;

	@Override
	public Money getBuy() {
		double minimum = Math.min(low, Math.min(open, close));
		return new Money(minimum);
	}

	@Override
	public Money getSell() {
		double maximum = Math.max(high, Math.max(open, close));
		return new Money(maximum);
	}

	@Override
	public LocalDateTime getDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(date), TimeZone.getDefault().toZoneId());
	}
}
