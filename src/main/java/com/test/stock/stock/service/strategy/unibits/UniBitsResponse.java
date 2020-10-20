package com.test.stock.stock.service.strategy.unibits;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.test.stock.stock.service.strategy.model.StockResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by koseungbin on 2020-10-17
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class UniBitsResponse implements StockResponse {
	private MetaData meta_data;
	private Map<String, List<UniBitsStockPrice>> result_data;

	@Override
	public List<UniBitsStockPrice> getPrices() {
		for(String key : result_data.keySet()) {
			return result_data.get(key);
		}

		return Collections.emptyList();
	}

	@Override
	public boolean isInvalid() {
		return meta_data == null || result_data == null || result_data.isEmpty();
	}

}
