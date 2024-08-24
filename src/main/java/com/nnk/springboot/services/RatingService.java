package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.Rating;

public interface RatingService {
	// Method declarations that will be implemented in RatingServiceImpl class

	List<Rating> findAllRatings();

	Rating save(Rating rating);

	Optional<Rating> findById(Integer id);

	void delete(Rating rating);
}
