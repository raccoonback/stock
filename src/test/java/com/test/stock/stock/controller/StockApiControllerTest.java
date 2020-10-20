package com.test.stock.stock.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.Price;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockInfo;
import com.test.stock.stock.service.StockService;

/**
 * Created by koseungbin on 2020-10-21
 */

@WebMvcTest
class StockApiControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private StockService stockService;

	@Test
	void shouldResponseEmptyStockProfit_ifHasNotProfitSegment() throws Exception {
		// given
		String symbol = "TEST";
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(100), new Money(100))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(50), new Money(50))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(2), new Price(new Money(1), new Money(1))));

		StockInfo stockInfo = new StockInfo(stockPrice);

		given(stockService.findStockStatistics(symbol)).willReturn(stockInfo);

		// when
		final ResultActions actions = mvc.perform(get("/api/stock/yahoo/{symbol}", symbol)
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		actions.andExpect(status().isOk())
			.andExpect(jsonPath("stockProfit").isEmpty())
			.andExpect(jsonPath("stockPrices[0].date").value(stockPrice.get(0).getDate().toString()))
			.andExpect(jsonPath("stockPrices[0].minPrice").value(stockPrice.get(0).getPrice().getBuy().getValue()))
			.andExpect(jsonPath("stockPrices[0].maxPrice").value(stockPrice.get(0).getPrice().getSell().getValue()))
			.andExpect(jsonPath("stockPrices[1].date").value(stockPrice.get(1).getDate().toString()))
			.andExpect(jsonPath("stockPrices[1].minPrice").value(stockPrice.get(1).getPrice().getBuy().getValue()))
			.andExpect(jsonPath("stockPrices[1].maxPrice").value(stockPrice.get(1).getPrice().getSell().getValue()))
			.andExpect(jsonPath("stockPrices[2].date").value(stockPrice.get(2).getDate().toString()))
			.andExpect(jsonPath("stockPrices[2].minPrice").value(stockPrice.get(2).getPrice().getBuy().getValue()))
			.andExpect(jsonPath("stockPrices[2].maxPrice").value(stockPrice.get(2).getPrice().getSell().getValue()));
	}
}
