package com.test.stock.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.stock.stock.controller.response.ApiStockResponse;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockInfo;
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
	@GetMapping(value = "/api/stock/yahoo/{symbol}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiStockResponse profitByYahoo(@PathVariable String symbol) {
		StockInfo stockInfo = yahooStockService.findStockStatistics(symbol);
		return ApiStockResponse.from(stockInfo);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/api/stock/unibits/{symbol}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ApiStockResponse profitByUniBits(@PathVariable String symbol) {
		StockInfo stockInfo = unibitsStockService.findStockStatistics(symbol);
		return ApiStockResponse.from(stockInfo);
	}
}
