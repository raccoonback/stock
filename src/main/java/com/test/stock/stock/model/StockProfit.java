package com.test.stock.stock.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by koseungbin on 2020-10-18
 */

@Getter
@AllArgsConstructor
public class StockProfit {
	private LocalDate start;
	private LocalDate end;
	private Money profit;

	public void update(LocalDate start, LocalDate end, Money profit) {
		this.start = start;
		this.end = end;
		this.profit = profit;
	}
}
