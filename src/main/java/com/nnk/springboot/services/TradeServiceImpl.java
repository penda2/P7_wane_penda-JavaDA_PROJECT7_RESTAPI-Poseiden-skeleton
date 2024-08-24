package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/*implementation of methods declared 
 * in the TradeService interface
 */
@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	@Override
	public List<Trade> findAllTrades() {
		return tradeRepository.findAll();
	}

	@Override
	public Trade save(Trade trade) {
		return tradeRepository.save(trade);
	}

	@Override
	public Optional<Trade> findById(Integer id) {
		return tradeRepository.findById(id);
	}

	@Override
	public void delete(Trade trade) {
		tradeRepository.delete(trade);
	}
}
