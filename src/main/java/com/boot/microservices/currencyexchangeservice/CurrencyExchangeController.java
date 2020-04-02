package com.boot.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	@Autowired
	private CurrencyExchangeRepository exchangeRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrencyExchange(@PathVariable String from, @PathVariable String to) {
		//CurrencyExchange exchangeInfo = new CurrencyExchange(1l, from, to, new BigDecimal(60.04));
		CurrencyExchange exchangeInfo = exchangeRepository.findByFromAndTo(from, to);
		exchangeInfo.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		logger.info("Exchange Rate {} -> {} is {}", from, to, exchangeInfo.getConversionRate());
		return exchangeInfo;
	}
}
