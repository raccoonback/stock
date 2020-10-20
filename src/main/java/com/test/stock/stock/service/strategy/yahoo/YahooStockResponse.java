package com.test.stock.stock.service.strategy.yahoo;

import java.sql.Timestamp;
import java.util.List;

import com.test.stock.stock.service.strategy.model.StockResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by koseungbin on 2020-10-17
 */

@Setter
@NoArgsConstructor
@AllArgsConstructor
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
