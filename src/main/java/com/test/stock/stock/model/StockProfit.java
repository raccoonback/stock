package com.test.stock.stock.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-18
 */

@Getter
@AllArgsConstructor
@ToString
public class StockProfit {
	private LocalDate start;
	private LocalDate end;
	private Money profit;
}
