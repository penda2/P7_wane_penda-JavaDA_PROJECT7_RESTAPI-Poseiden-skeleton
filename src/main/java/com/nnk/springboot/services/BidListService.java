package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.BidList;

// Method declarations that will be implemented in BidListServiceImpl class
public interface BidListService {

	BidList save(BidList bidList);

	List<BidList> findAll();

	Optional<BidList> findById(Integer id);

	void delete(BidList bidList);
}
