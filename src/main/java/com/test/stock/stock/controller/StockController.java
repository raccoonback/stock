package com.test.stock.stock.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by koseungbin on 2020-10-18
 */

@Controller
public class StockController {
	@GetMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("info", Arrays.asList("yahoo", "unibits"));
		model.addAttribute("initSymbol", "AAPL");
		return "index";
	}
}
