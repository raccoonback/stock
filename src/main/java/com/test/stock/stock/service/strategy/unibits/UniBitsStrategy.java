package com.test.stock.stock.service.strategy.unibits;

import org.springframework.http.HttpEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.stock.stock.model.Symbol;
import com.test.stock.stock.service.strategy.AbsStrategy;
import com.test.stock.stock.service.strategy.model.StockResponse;
import com.test.stock.stock.service.strategy.yahoo.Period;

import lombok.RequiredArgsConstructor;

/**
 * Created by koseungbin on 2020-10-19
 */

@RequiredArgsConstructor
public class UniBitsStrategy extends AbsStrategy {
	private static final String HOST = "https://api.unibit.ai";
	private final Symbol symbol;
	private final Period period;
	private final String uniBitsApiKey;

	@Override
	public String getUri() {
		return UriComponentsBuilder.fromHttpUrl(HOST)
			.path("v2/stock/historical/")
			.queryParam("tickers", symbol.getValue())
			.queryParam("selectedFields", "all")
			.queryParam("interval", 1)
			.queryParam("startDate", period.getStartDate().toLocalDate())
			.queryParam("endDate", period.getEndDate().toLocalDate())
			.queryParam("dataType", "json")
			.queryParam("accessKey", uniBitsApiKey)
			.toUriString();
	}

	@Override
	public HttpEntity getHttpEntity() {
		return HttpEntity.EMPTY;
	}

	@Override
	public Class<? extends StockResponse> getResponseType() {
		return UniBitsResponse.class;
	}

	@Override
	public Period getPeriod() {
		return period;
	}
}
