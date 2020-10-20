package com.test.stock.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.stock.stock.controller.response.ApiStockResponse;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.service.StockService;

import lombok.RequiredArgsConstructor;

/**
 * Created by koseungbin on 2020-10-18
 */

@RestController
@RequiredArgsConstructor
public class StockApiController {

	@Qualifier(value = "yahooStockService")
	private final StockService yahooStockService;

	@Qualifier(value = "unibitsStockService")
	private final StockService unibitsStockService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/api/stock/yahoo/{symbol}")
	public ApiStockResponse profitByYahoo(@PathVariable String symbol) {
		StockProfit stockProfit = yahooStockService.choice(symbol);
		List<StockFluctuationPrice> stockPrices = yahooStockService.find(symbol);
		return ApiStockResponse.from(stockProfit, stockPrices);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/api/stock/unibits/{symbol}")
	public ApiStockResponse profitByUniBits(@PathVariable String symbol) {
		StockProfit stockProfit = unibitsStockService.choice(symbol);
		List<StockFluctuationPrice> stockPrices = unibitsStockService.find(symbol);
		return ApiStockResponse.from(stockProfit, stockPrices);
	}
}
