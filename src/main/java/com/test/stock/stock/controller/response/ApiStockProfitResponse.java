package com.test.stock.stock.controller.response;

import java.time.LocalDate;

import com.test.stock.stock.model.StockProfit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-18
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ApiStockProfitResponse {
	private LocalDate startDate;
	private LocalDate endDate;
	private Double profit;

	public static ApiStockProfitResponse from(StockProfit stockProfit) {
		return new ApiStockProfitResponse(stockProfit.getStart(), stockProfit.getEnd(),
			stockProfit.getProfit().getValue());
	}
}
