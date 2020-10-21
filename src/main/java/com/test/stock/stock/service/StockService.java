package com.test.stock.stock.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.test.stock.exception.NotMeasurableException;
import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockInfo;
import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.model.Symbol;
import com.test.stock.stock.repository.StockRepository;
import com.test.stock.stock.service.strategy.Strategy;
import com.test.stock.stock.service.strategy.yahoo.Period;
import com.test.stock.stock.service.strategy.yahoo.frequency.Frequency;
import com.test.stock.stock.service.strategy.yahoo.frequency.type.Day;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by koseungbin on 2020-10-17
 */

@Slf4j
@Validated
@RequiredArgsConstructor
public abstract class StockService {
	protected final StockRepository stockRepository;

	abstract Strategy getStrategy(Symbol symbol, Frequency frequency);

	abstract StockFluctuationPrice adjustSpareStockPrice(Symbol symbol, Period period);

	public StockInfo findStockStatistics(Symbol symbol) {
		return findStockStatistics(symbol, new Day());
	}

	public StockInfo findStockStatistics(@Valid Symbol symbol, Frequency frequency) {
		List<StockFluctuationPrice> pretreatedStockPrices = investigate(symbol, frequency);
		try {
			StockProfit stockProfit = search(pretreatedStockPrices);
			return new StockInfo(stockProfit, pretreatedStockPrices);
		} catch (NotMeasurableException exception) {
			log.info("not found max profit segment", exception);
			return new StockInfo(pretreatedStockPrices);
		}
	}

	public StockProfit findStockProfit(Symbol symbol) {
		return findStockProfit(symbol, new Day());
	}

	public StockProfit findStockProfit(@Valid Symbol symbol, Frequency frequency) {
		List<StockFluctuationPrice> pretreatedStockPrices = investigate(symbol, frequency);
		return search(pretreatedStockPrices);
	}

	public List<StockFluctuationPrice> investigate(@Valid Symbol symbol, Frequency frequency) {
		Strategy strategy = getStrategy(symbol, frequency);
		List<StockFluctuationPrice> stockPrices = stockRepository.find(strategy);

		StockFluctuationPrice spareStockPrice = adjustSpareStockPrice(symbol, strategy.getPeriod());
		stockPrices.add(spareStockPrice);

		return pretreat(stockPrices);
	}

	private List<StockFluctuationPrice> pretreat(List<StockFluctuationPrice> stockPrices) {
		return stockPrices
			.stream()
			.filter(Objects::nonNull)
			.filter(StockFluctuationPrice::validate)
			.sorted(Comparator.comparing(StockFluctuationPrice::getDate))
			.collect(Collectors.toList());
	}

	public StockProfit search(List<StockFluctuationPrice> stockFluctuationPrices) {
		int minIndex = 0, maxIndex = 1, minFluctuationSize = 2;
		if (stockFluctuationPrices == null || stockFluctuationPrices.size() <= maxIndex) {
			throw new NotMeasurableException("최대 수익을 측정할 수 있는 데이터가 부족합니다");
		}

		StockFluctuationPrice minStockPrice = stockFluctuationPrices.get(minIndex);
		StockFluctuationPrice maxStockPrice = stockFluctuationPrices.get(maxIndex);
		Money maxProfit = initializeMaxProfit(minStockPrice, maxStockPrice);
		StockProfit stockProfit =  new StockProfit(minStockPrice.getDate(), maxStockPrice.getDate(), maxProfit);
		if(stockFluctuationPrices.size() > minFluctuationSize) {
			for (StockFluctuationPrice stockFluctuationPrice : stockFluctuationPrices) {
				try {
					Money profit = stockFluctuationPrice.getPrice().getSell().subtract(minStockPrice.getPrice().getBuy());
					if (profit.getValue() > stockProfit.getProfit().getValue()) {
						maxStockPrice = stockFluctuationPrice;
						stockProfit.update(minStockPrice.getDate(), maxStockPrice.getDate(), profit);
					}
				} catch (IllegalStateException exception) {
					// 현재 수익 금액(Money)가 음수인 경우에 에러가 발생
					// ignored
				} finally {
					if (stockFluctuationPrice.getPrice().getBuy().getValue() < minStockPrice.getPrice()
						.getBuy()
						.getValue()) {
						minStockPrice = stockFluctuationPrice;
					}
				}
			}
		}

		if (maxProfit.hasNotBalance()) {
			throw new NotMeasurableException("최대 수익이 가능한 상승 구간이 없습니다.");
		}

		return stockProfit;
	}

	private Money initializeMaxProfit(StockFluctuationPrice minStockPrice, StockFluctuationPrice maxStockPrice) {
		try {
			return maxStockPrice.getPrice().getSell().subtract(minStockPrice.getPrice().getBuy());
		} catch (IllegalStateException exception) {
			return new Money(0.0);
		}
	}
}
