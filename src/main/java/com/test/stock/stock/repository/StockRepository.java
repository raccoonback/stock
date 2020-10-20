package com.test.stock.stock.repository;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.test.stock.exception.IllegalThirdpartyRequestException;
import com.test.stock.stock.model.StockFluctuationPrice;
import com.test.stock.stock.repository.strategy.Strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by koseungbin on 2020-10-17
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockRepository {

	private final RestTemplate restTemplate;

	public List<StockFluctuationPrice> find(Strategy strategy) {
		ResponseEntity<? extends StockResponse> response = restTemplate
			.exchange(strategy.getUri(), HttpMethod.GET, strategy.getHttpEntity(), strategy.getResponseType());

		if (response.getStatusCode().isError()) { // 4XX or 5XX
			throw new IllegalThirdpartyRequestException(strategy.getUri() + " 요청 과정에서 에러 발생");
		}

		return strategy.transfer(response.getBody());
	}

}
