package com.test.stock.stock.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.stock.exception.NotFoundException;
import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.Price;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.repository.StockRepository;
import com.test.stock.stock.repository.strategy.Strategy;
import com.test.stock.stock.repository.strategy.yahoo.Period;
import com.test.stock.stock.repository.strategy.yahoo.YahooStrategy;
import com.test.stock.stock.repository.strategy.yahoo.frequency.Frequency;
import com.test.stock.utils.DateUtils;

/**
 * Created by koseungbin on 2020-10-18
 */

@Service(value = "yahooStockService")
public class YahooStockService extends StockService {
	@Value("${rapid-api.host}")
	private String RAPID_API_HOST;

	@Value("${rapid-api.key}")
	private String RAPID_API_KEY;

	private static final Long PERIOD = 180L;

	public YahooStockService(StockRepository stockRepository) {
		super(stockRepository);
	}

	@Override
	protected Strategy getStrategy(String symbol, Frequency frequency) {
		Period period = new Period(LocalDate.now().atStartOfDay(), PERIOD, frequency);
		return new YahooStrategy(symbol, period, RAPID_API_HOST, RAPID_API_KEY);
	}

	@Override
	StockFluctuationPrice adjustSpareStockPrice(String symbol, Period period) {
		Strategy strategy = new YahooStrategy(symbol, period.asSparePeriod(), RAPID_API_HOST, RAPID_API_KEY);
		try {
			List<StockFluctuationPrice> stockPrices = stockRepository.find(strategy);
			Money min = minBuy(stockPrices);
			Money max = maxSell(stockPrices);
			return new StockFluctuationPrice(DateUtils.toLocalDate(strategy.getPeriod().getTailoredStartDateAccordingToFrequency()), new Price(min, max));
		} catch (NotFoundException exception) {
			// 조정해야할 날짜가 없는 경우
			return null;
		}
	}

	private Money minBuy(List<StockFluctuationPrice> stockFluctuationPrices) {
		return stockFluctuationPrices.stream()
			.map(StockFluctuationPrice::getPrice)
			.map(Price::getBuy)
			.min(Comparator.comparingDouble(Money::getValue))
			.orElse(new Money(0.0));
	}

	private Money maxSell(List<StockFluctuationPrice> stockFluctuationPrices) {
		return stockFluctuationPrices.stream()
			.map(StockFluctuationPrice::getPrice)
			.map(Price::getSell)
			.max(Comparator.comparingDouble(Money::getValue))
			.orElse(new Money(0.0));
	}
}
