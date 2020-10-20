package com.test.stock.stock.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by koseungbin on 2020-10-21
 */

@AllArgsConstructor
@Getter
public class StockInfo {
	private StockProfit stockProfit;
	private List<StockFluctuationPrice> stockFluctuationPrices;

	public StockInfo(List<StockFluctuationPrice> stockFluctuationPrices) {
		this.stockFluctuationPrices = stockFluctuationPrices;
	}

	public boolean hasProfit() {
		return stockProfit != null;
	}
}
