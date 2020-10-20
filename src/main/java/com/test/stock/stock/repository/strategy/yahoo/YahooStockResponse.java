package com.test.stock.stock.repository.strategy.yahoo;

import java.sql.Timestamp;
import java.util.List;

import com.test.stock.stock.repository.StockResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-17
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YahooStockResponse implements StockResponse {
	private List<YahooStockPrice> prices;
	private boolean isPending;
	private Timestamp firstTradeDate;
	private String id;

	@Override
	public List<YahooStockPrice> getPrices() {
		return prices;
	}

	@Override
	public boolean isInvalid() {
		return prices == null || prices.isEmpty();
	}
}
