package com.test.stock.stock.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.test.stock.stock.model.StockInfo;

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
public class ApiStockResponse {
	private ApiStockProfitResponse stockProfit;
	private List<ApiStockPriceResponse> stockPrices;

	private ApiStockResponse(List<ApiStockPriceResponse> stockPrices) {
		this.stockPrices = stockPrices;
	}

	public static ApiStockResponse from(StockInfo stockInfo) {
		List<ApiStockPriceResponse> stockPrices = stockInfo.getStockFluctuationPrices()
			.stream()
			.map(ApiStockPriceResponse::from)
			.collect(Collectors.toList());

		if (stockInfo.hasProfit()) {
			ApiStockProfitResponse profit = ApiStockProfitResponse.from(stockInfo.getStockProfit());
			return new ApiStockResponse(profit, stockPrices);
		}

		return new ApiStockResponse(stockPrices);
	}
}
