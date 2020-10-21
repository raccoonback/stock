package com.test.stock.stock.service.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import com.test.stock.exception.NotMeasurableException;
import com.test.stock.stock.model.Money;
import com.test.stock.stock.model.Price;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.model.StockProfit;
import com.test.stock.stock.service.YahooStockService;

/**
 * Created by koseungbin on 2020-10-21
 */

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

	@InjectMocks
	@Qualifier("yahooStockService")
	YahooStockService stockService;

	@Test
	@DisplayName("Null 파리미터 전달시 NotMeasurableException 예외가 발생해야만 한다.")
	void shouldThrowNotMeasurableException_ifInvalidParameter() {
		// given && when && then
		assertThrows(NotMeasurableException.class, () -> stockService.search(null));
	}

	@Test
	@DisplayName("주가 개수가 1개인 경우 NotMeasurableException 예외가 발생해야만 한다.")
	void shouldThrowNotMeasurableException_ifSingleSize() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(100), new Money(100))));

		// when && then
		assertThrows(NotMeasurableException.class, () -> stockService.search(stockPrice));
	}

	@Test
	@DisplayName("두 개의 주가가 존재할 경우 반드시 두 번째 매도 가격이 첫 번째 매수 가격보다 커야만 한다.")
	void shouldSecondSellPrice_greaterThan_FirstBuyPrice() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(50), new Money(100))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(10), new Money(60))));

		// when
		StockProfit stockProfit = stockService.search(stockPrice);

		// then
		assertEquals(new Money(10.0), stockProfit.getProfit());
		assertEquals(stockPrice.get(0).getDate(), stockProfit.getStart());
		assertEquals(stockPrice.get(1).getDate(), stockProfit.getEnd());
	}

	@Test
	@DisplayName("두 개의 주가가 존재할 경우 상승하는 구간이 없다면 NotMeasurableException 예외가 발생해야만 한다.")
	void shouldThrowNotMeasurableException_betweenTwoStockPrices() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(80), new Money(100))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(10), new Money(60))));

		// when && then
		assertThrows(NotMeasurableException.class, () -> stockService.search(stockPrice));
	}

	@Test
	@DisplayName("두 개의 주가가 존재할 경우 일정한 매도/매수 가 구간이라 NotMeasurableException 예외가 발생해야만 한다.")
	void shouldThrowNotMeasurableException_eqaulsBetweenBuyAndSell() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(60), new Money(100))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(10), new Money(60))));

		// when && then
		assertThrows(NotMeasurableException.class, () -> stockService.search(stockPrice));
	}

	@Test
	@DisplayName("상승하는 구간이 없는 경우에는 NotMeasurableException 예외가 발생해야만 한다.")
	void shouldThrowNotMeasurableException_ifNotMeasurableSegment() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(100), new Money(100))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(50), new Money(50))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(2), new Price(new Money(1), new Money(1))));

		// when && then
		assertThrows(NotMeasurableException.class, () -> stockService.search(stockPrice));
	}

	@Test
	@DisplayName("초기 설정인 0, 1 인덱스의 초기 상승 구간이 최대 수익이어야만 한다.")
	void shouldMaxProfit_InitSegment() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(0), new Money(100))));
		stockPrice.add(
			new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(200), new Money(200))));
		stockPrice.add(new StockFluctuationPrice(LocalDate.now().plusDays(2), new Price(new Money(1), new Money(1))));

		// when
		StockProfit stockProfit = stockService.search(stockPrice);

		//then
		assertEquals(new Money(200.0), stockProfit.getProfit());
		assertEquals(stockPrice.get(0).getDate(), stockProfit.getStart());
		assertEquals(stockPrice.get(1).getDate(), stockProfit.getEnd());
	}

	@Test
	@DisplayName("중간 구간의 2, 4 인덱스의 초기 상승 구간이 최대 수익이어야만 한다.")
	void shouldMaxProfit_middleSegment() {
		// given
		List<StockFluctuationPrice> stockPrice = new ArrayList<>();
		stockPrice.add(new StockFluctuationPrice(LocalDate.now(), new Price(new Money(50), new Money(100))));
		stockPrice.add(
			new StockFluctuationPrice(LocalDate.now().plusDays(1), new Price(new Money(10), new Money(200))));
		stockPrice.add(
			new StockFluctuationPrice(LocalDate.now().plusDays(2), new Price(new Money(20), new Money(300))));
		stockPrice.add(
			new StockFluctuationPrice(LocalDate.now().plusDays(3), new Price(new Money(30), new Money(400))));

		// when
		StockProfit stockProfit = stockService.search(stockPrice);

		//then
		assertEquals(new Money(390.0), stockProfit.getProfit());
		assertEquals(stockPrice.get(1).getDate(), stockProfit.getStart());
		assertEquals(stockPrice.get(3).getDate(), stockProfit.getEnd());
	}
}
