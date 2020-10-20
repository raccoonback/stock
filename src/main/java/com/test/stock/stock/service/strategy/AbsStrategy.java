package com.test.stock.stock.service.strategy;

import java.util.List;
import java.util.stream.Collectors;

import com.test.stock.exception.NotFoundException;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.service.strategy.model.StockPrice;
import com.test.stock.stock.service.strategy.model.StockResponse;

/**
 * Created by koseungbin on 2020-10-20
 */

public abstract class AbsStrategy implements Strategy {
	@Override
	public List<StockFluctuationPrice> transfer(StockResponse prices) {
		if(prices == null || prices.isInvalid()) {
			throw new NotFoundException("존재하지 않는 종목입니다. 다시 확인해주세요.");
		}

		return prices.getPrices()
			.stream()
			.map(StockPrice::toStockFluctuationPrice)
			.collect(Collectors.toList());
	}
}
