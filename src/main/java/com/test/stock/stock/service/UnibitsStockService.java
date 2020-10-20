package com.test.stock.stock.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.repository.StockRepository;
import com.test.stock.stock.service.strategy.Strategy;
import com.test.stock.stock.service.strategy.unibits.UniBitsStrategy;
import com.test.stock.stock.service.strategy.yahoo.Period;
import com.test.stock.stock.service.strategy.yahoo.frequency.Frequency;

/**
 * Created by koseungbin on 2020-10-19
 */

@Service("unibitsStockService")
public class UnibitsStockService extends StockService {
	private static final Long PERIOD = 180L;

	@Value("${unibits-api.key}")
	private String UNIBITS_API_KEY;

	public UnibitsStockService(StockRepository stockRepository) {
		super(stockRepository);
	}

	@Override
	protected Strategy getStrategy(String symbol, Frequency frequency) {
		Period period = new Period(LocalDate.now().atStartOfDay(), PERIOD, frequency);
		return new UniBitsStrategy(symbol, period, UNIBITS_API_KEY);
	}

	@Override
	StockFluctuationPrice adjustSpareStockPrice(String symbol, Period period) {
		return null;
	}
}
