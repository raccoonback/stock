package com.test.stock.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by koseungbin on 2020-10-18
 */

@Controller
public class StockController {
	@GetMapping(value = "/")
	public String index() {
		System.out.println("test");
		return "index";
	}
}
