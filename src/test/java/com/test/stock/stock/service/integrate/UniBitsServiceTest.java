package com.test.stock.stock.service.integrate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.model.Symbol;
import com.test.stock.stock.service.StockService;

/**
 * Created by koseungbin on 2020-10-19
 */

@SpringBootTest
class UniBitsServiceTest {
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
		Symbol symbol = new Symbol("AAPL");
		StockProfit profitByDayUsingYahoo = stockService.findStockProfit(symbol);

		// when
		StockProfit profitUsingUniBits = uniBitsService.findStockProfit(symbol);

		// then
		assertEquals(profitByDayUsingYahoo.getStart(), profitUsingUniBits.getStart());
		assertEquals(profitByDayUsingYahoo.getEnd(), profitUsingUniBits.getEnd());
		assertEquals(Math.round(profitByDayUsingYahoo.getProfit().getValue()),
			Math.round(profitUsingUniBits.getProfit().getValue()));
	}

	@Test
	@DisplayName("구글 - UniBits와 Yahoo Thirdparty api 에 대한 결과가 동일해야만 한다.")
	void shouldSame_uniBits_and_yahoo_maxProfit_ofGoogle() {
		// given
		Symbol symbol = new Symbol("GOOG");
		StockProfit profitByDayUsingYahoo = stockService.findStockProfit(symbol);

		// when
		StockProfit profitUsingUniBits = uniBitsService.findStockProfit(symbol);

		// then
		assertEquals(profitByDayUsingYahoo.getStart(), profitUsingUniBits.getStart());
		assertEquals(profitByDayUsingYahoo.getEnd(), profitUsingUniBits.getEnd());
		assertEquals(Math.round(profitByDayUsingYahoo.getProfit().getValue()),
			Math.round(profitUsingUniBits.getProfit().getValue()));

	}
}
