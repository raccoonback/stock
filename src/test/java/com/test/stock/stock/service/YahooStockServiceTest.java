package com.test.stock.stock.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.model.Symbol;
import com.test.stock.stock.service.strategy.yahoo.frequency.type.Month;
import com.test.stock.stock.service.strategy.yahoo.frequency.type.Week;

/**
 * Created by koseungbin on 2020-10-17
 */

@SpringBootTest
class YahooStockServiceTest {
	@Autowired
	@Qualifier("yahooStockService")
	private StockService stockService;

	@Test
	@DisplayName("구글 - 일 단위와 월 단위의 최대 수익이 동일해야만 한다.")
	void shouldSameGoogleMaxProfit_betweenDaysAndMonth() {
		// given
		Symbol symbol = new Symbol("GOOG");
		StockProfit profitByWeeks = stockService.findStockProfit(symbol, new Month());

		// when
		StockProfit profitByDays = stockService.findStockProfit(symbol);

		// then
		assertEquals(profitByDays.getProfit().getValue(), profitByWeeks.getProfit().getValue());
	}

	@Test
	@DisplayName("구글 - 일 단위와 주 단위의 최대 수익이 동일해야만 한다.")
	void shouldSameGoogleMaxProfit_betweenDaysAndWeeks() {
		// given
		Symbol symbol = new Symbol("GOOG");
		StockProfit profitByWeeks = stockService.findStockProfit(symbol, new Week());

		// when
		StockProfit profitByDays = stockService.findStockProfit(symbol);

		// then
		assertEquals(profitByDays.getProfit().getValue(), profitByWeeks.getProfit().getValue());
	}

	@Test
	@DisplayName("애플 - 일 단위와 월 단위의 최대 수익이 동일해야만 한다.")
	void shouldSameAppleMaxProfit_betweenDaysAndMonth() {
		// given
		Symbol symbol = new Symbol("AAPL");
		StockProfit profitByWeeks = stockService.findStockProfit(symbol, new Month());

		// when
		StockProfit profitByDays = stockService.findStockProfit(symbol);

		// then
		assertEquals(profitByDays.getProfit().getValue(), profitByWeeks.getProfit().getValue());
	}

	@Test
	@DisplayName("애플 - 일 단위와 주 단위의 최대 수익이 동일해야만 한다.")
	void shouldSameAppleMaxProfit_betweenDaysAndWeeks() {
		// given
		Symbol symbol = new Symbol("AAPL");
		StockProfit profitByWeeks = stockService.findStockProfit(symbol, new Week());

		// when
		StockProfit profitByDays = stockService.findStockProfit(symbol);

		// then
		assertEquals(profitByDays.getProfit().getValue(), profitByWeeks.getProfit().getValue());
	}
}
