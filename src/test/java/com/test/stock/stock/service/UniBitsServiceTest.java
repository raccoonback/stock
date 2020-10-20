package com.test.stock.stock.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.repository.strategy.yahoo.frequency.type.Day;

/**
 * Created by koseungbin on 2020-10-19
 */

@SpringBootTest
public class UniBitsServiceTest {

	@Autowired()
	@Qualifier("unibitsStockService")
	StockService uniBitsService;

	@Autowired
	@Qualifier("yahooStockService")
	private StockService stockService;

	@Test
	@DisplayName("애플 - UniBits와 Yahoo Thirdparty api 에 대한 결과가 동일해야만 한다.")
	void shouldSame_uniBits_and_yahoo_maxProfit_ofApple() {
		// given
		StockProfit profitByDayUsingYahoo = stockService.choice("AAPL", new Day());

		// when
		StockProfit profitUsingUniBits = uniBitsService.choice("AAPL");

		// then
		assertEquals(profitByDayUsingYahoo.getStart(),profitUsingUniBits.getStart());
		assertEquals(profitByDayUsingYahoo.getEnd(),profitUsingUniBits.getEnd());
		assertEquals(Math.round(profitByDayUsingYahoo.getProfit().getValue()), Math.round(profitUsingUniBits.getProfit().getValue()));
	}

	@Test
	@DisplayName("구글 - UniBits와 Yahoo Thirdparty api 에 대한 결과가 동일해야만 한다.")
	void shouldSame_uniBits_and_yahoo_maxProfit_ofGoogle() {
		// given
		StockProfit profitByDayUsingYahoo = stockService.choice("GOOG", new Day());

		// when
		StockProfit profitUsingUniBits = uniBitsService.choice("GOOG");

		// then
		assertEquals(profitByDayUsingYahoo.getStart(),profitUsingUniBits.getStart());
		assertEquals(profitByDayUsingYahoo.getEnd(),profitUsingUniBits.getEnd());
		assertEquals(Math.round(profitByDayUsingYahoo.getProfit().getValue()), Math.round(profitUsingUniBits.getProfit().getValue()));

	}
}
