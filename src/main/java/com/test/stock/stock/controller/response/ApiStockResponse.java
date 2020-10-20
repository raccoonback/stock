package com.test.stock.stock.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.test.stock.stock.model.StockFluctuationPrice;
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
public class ApiStockResponse {
	private ApiStockProfitResponse stockProfit;
	private List<ApiStockPriceResponse> stockPrices;

	public static ApiStockResponse from(StockProfit stockProfit, List<StockFluctuationPrice> stockFluctuationPrices) {
		List<ApiStockPriceResponse> stockPrices = stockFluctuationPrices.stream()
			.map(ApiStockPriceResponse::from)
			.collect(Collectors.toList());

		return new ApiStockResponse(ApiStockProfitResponse.from(stockProfit), stockPrices);
	}
}
