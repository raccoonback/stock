package com.test.stock.stock.service.strategy.model;

import java.util.List;

/**
 * Created by koseungbin on 2020-10-17
 */


public interface StockResponse {
	List<? extends StockPrice> getPrices();

	boolean isInvalid();
}
