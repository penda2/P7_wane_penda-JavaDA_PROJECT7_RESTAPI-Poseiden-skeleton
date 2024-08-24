package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/*implementation of methods declared 
 * in the BidListService interface
 */

@Service
public class BidListServiceImpl implements BidListService {
	// Dependency injection
	@Autowired
	private BidListRepository bidListRepository;

	@Override
	public List<BidList> findAll() {
		return bidListRepository.findAll();
	}

	@Override
	public BidList save(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	@Override
	public Optional<BidList> findById(Integer id) {
		return bidListRepository.findById(id);
	}

	@Override
	public void delete(BidList bidList) {
		bidListRepository.delete(bidList);
	}
}
