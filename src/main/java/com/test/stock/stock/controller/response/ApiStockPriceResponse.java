package com.test.stock.stock.controller.response;

import java.time.LocalDate;

import com.test.stock.stock.model.Price;
import com.test.stock.stock.model.StockFluctuationPrice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by koseungbin on 2020-10-20
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApiStockPriceResponse {
	private LocalDate date;
	private Double maxPrice;
	private Double minPrice;

	public static ApiStockPriceResponse from(StockFluctuationPrice stockFluctuationPrice) {
		Price price = stockFluctuationPrice.getPrice();
		return new ApiStockPriceResponse(stockFluctuationPrice.getDate(), price.getSell().getValue(),
			price.getBuy().getValue());
	}
}
