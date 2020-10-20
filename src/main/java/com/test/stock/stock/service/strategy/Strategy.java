package com.test.stock.stock.service.strategy;

import java.util.List;

import org.springframework.http.HttpEntity;

import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.service.strategy.model.StockResponse;
import com.test.stock.stock.service.strategy.yahoo.Period;

/**
 * Created by koseungbin on 2020-10-17
 */


public interface Strategy {
	String getUri();

	HttpEntity getHttpEntity();

	List<StockFluctuationPrice> transfer(StockResponse prices);

	Class<? extends StockResponse> getResponseType();

	Period getPeriod();
}
