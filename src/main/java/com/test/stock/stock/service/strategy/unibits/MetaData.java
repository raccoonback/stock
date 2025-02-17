package com.test.stock.stock.service.strategy.unibits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by koseungbin on 2020-10-19
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
	private String api_name;
	private long num_total_data_points;
	private long credit_cost;
	private String start_date;
	private String end_date;
}
