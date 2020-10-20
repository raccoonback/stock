package com.test.stock.stock.repository.strategy.yahoo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.stock.stock.repository.strategy.AbsStrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by koseungbin on 2020-10-18
 */

@RequiredArgsConstructor
public class YahooStrategy extends AbsStrategy {
	private static final String RAPID_API = "https://rapidapi.p.rapidapi.com";

	private final String symbol;

	@Getter
	private final Period period;
	private final String rapidApiHost;
	private final String rapidApiKey;

	@Override
	public String getUri() {
		return UriComponentsBuilder.fromHttpUrl(RAPID_API)
			.path("stock/v2/get-historical-data")
			.queryParam("period1", period.getTailoredStartDateAccordingToFrequency())
			.queryParam("period2", period.getEndTimestamp())
			.queryParam("symbol", symbol)
			.queryParam("frequency", period.getFrequency().getSymbol())
			.queryParam("filter", "history")
			.toUriString();
	}

	@Override
	public HttpEntity getHttpEntity() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("x-rapidapi-host", rapidApiHost);
		httpHeaders.add("x-rapidapi-key", rapidApiKey);
		return new HttpEntity(httpHeaders);
	}

	@Override
	public Class<YahooStockResponse> getResponseType() {
		return YahooStockResponse.class;
	}
}
