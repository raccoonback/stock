package com.test.stock.stock.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.repository.StockRepository;
import com.test.stock.stock.repository.strategy.Strategy;
import com.test.stock.stock.repository.strategy.yahoo.frequency.type.Day;
import com.test.stock.stock.repository.strategy.yahoo.frequency.Frequency;
import com.test.stock.stock.repository.strategy.yahoo.Period;

import lombok.RequiredArgsConstructor;

/**
 * Created by koseungbin on 2020-10-17
 */

@RequiredArgsConstructor
public abstract class StockService {

	protected final StockRepository stockRepository;

	abstract Strategy getStrategy(String symbol, Frequency frequency);

	abstract StockFluctuationPrice adjustSpareStockPrice(String symbol, Period period);

	public StockProfit choice(String symbol) {
		return choice(symbol, new Day());
	}

	public StockProfit choice(String symbol, Frequency frequency) {
		List<StockFluctuationPrice> pretreatedStockPrices = investigate(symbol, frequency);
		return search(pretreatedStockPrices);
	}

	public List<StockFluctuationPrice> investigate(String symbol) {
		return investigate(symbol, new Day());
	}

	public List<StockFluctuationPrice> investigate(String symbol, Frequency frequency) {
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

	private StockProfit search(List<StockFluctuationPrice> stockFluctuationPrices) {
		Optional<StockProfit> maximumStockProfit = Optional.empty();
		for (int start = 0; start < stockFluctuationPrices.size(); ++start) {
			for (int end = start + 1; end < stockFluctuationPrices.size(); ++end) {
				StockFluctuationPrice startStockPrice = stockFluctuationPrices.get(start);
				StockFluctuationPrice endStockPrice = stockFluctuationPrices.get(end);
				Money buy = startStockPrice.getPrice().getBuy();
				Money sell = endStockPrice.getPrice().getSell();
				try {
					Money profit = sell.subtract(buy);
					if (profit.hasBalance()) {
						StockProfit stockProfit = new StockProfit(startStockPrice.getDate(), endStockPrice.getDate(),
							profit);
						if (maximumStockProfit.isPresent()) {
							Money rest = stockProfit.getProfit().subtract(maximumStockProfit.get().getProfit());
							if (rest.hasBalance()) {
								maximumStockProfit = Optional.of(stockProfit);
							}

						} else {
							maximumStockProfit = Optional.of(stockProfit);
						}
					}
				} catch (Exception error) {
					// Money 객체가 음수가 된 경우
					// ignored
				}
			}
		}

		return maximumStockProfit.orElseThrow(() -> new IllegalStateException("적합한 범위가 없습니다."));
	}
}
