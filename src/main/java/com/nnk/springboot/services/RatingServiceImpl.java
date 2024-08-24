package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/*implementation of methods declared 
 * in the RatingService interface
 */
@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public List<Rating> findAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Optional<Rating> findById(Integer id) {
		return ratingRepository.findById(id);
	}

	@Override
	public void delete(Rating rating) {
		ratingRepository.delete(rating);
	}

}
