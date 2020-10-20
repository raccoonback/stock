package com.test.stock.stock.service.strategy.model;

import java.time.LocalDateTime;

import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.Price;
import com.test.stock.stock.model.StockFluctuationPrice;

/**
 * Created by koseungbin on 2020-10-18
 */

public interface StockPrice {
	Money getBuy();
	Money getSell();
	LocalDateTime getDate();

	default StockFluctuationPrice toStockFluctuationPrice() {
		return new StockFluctuationPrice(getDate().toLocalDate(), new Price(getBuy(), getSell()));
	}
}
