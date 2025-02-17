package com.test.stock.stock.service.strategy.unibits;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.test.stock.stock.model.Money;
import com.test.stock.stock.service.strategy.model.StockPrice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by koseungbin on 2020-10-18
 */

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniBitsStockPrice implements StockPrice {
	private LocalDate date;
	@Getter
	private double open;
	@Getter
	private double high;
	@Getter
	private double low;
	@Getter
	private double close;
	@Getter
	private double adj_close;

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
		return date.atStartOfDay();
	}
}
