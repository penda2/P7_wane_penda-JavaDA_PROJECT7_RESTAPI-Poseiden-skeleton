package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.Trade;

public interface TradeService {
	// Method declarations that will be implemented in TradeServiceImpl class

	List<Trade> findAllTrades();

	Trade save(Trade trade);

	Optional<Trade> findById(Integer id);

	void delete(Trade trade);
}
