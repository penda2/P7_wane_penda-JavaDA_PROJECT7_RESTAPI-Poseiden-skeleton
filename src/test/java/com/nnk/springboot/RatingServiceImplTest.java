package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {

	@Mock // test the service without depending on the database
	private RatingRepository ratingRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private RatingServiceImpl ratingServiceImpl;

	private Rating rating;

	@BeforeEach // initializes a Rating before each test
	public void setUp() {
		rating = new Rating();
		rating.setId(1);
		rating.setFitchRating("test fitch");
		rating.setMoodysRating("Moody");
		rating.setOrderNumber(2);
		rating.setSandPRating("Stand 2");
	}

	// test Find All Ratings method
	@Test
	public void testFindAllRatings() {
		// Arrange
		List<Rating> ratings = Arrays.asList(rating, new Rating());
		when(ratingRepository.findAll()).thenReturn(ratings);

		// Act
		List<Rating> result = ratingServiceImpl.findAllRatings();

		// Assert
		verify(ratingRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save Rating method
	@Test
	public void testSaveRating() {
		// Arrange
		when(ratingRepository.save(rating)).thenReturn(rating);

		// Act
		Rating result = ratingServiceImpl.save(rating);

		// Assert
		verify(ratingRepository, times(1)).save(rating);
		assertEquals(rating.getFitchRating(), result.getFitchRating());
		assertEquals(rating.getMoodysRating(), result.getMoodysRating());
	}

	// test find Rating By Id method
	@Test
	public void testFindRatingById() {
		// Arrange
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		// Act
		Optional<Rating> result = ratingServiceImpl.findById(1);

		// Assert
		verify(ratingRepository, times(1)).findById(1);
		assertTrue(result.isPresent());
		assertEquals(rating.getFitchRating(), result.get().getFitchRating());
	}

	// test Delete a Rating method
	@Test
	public void testDeleteRating() {
		// Arrange
		doNothing().when(ratingRepository).delete(rating);

		// Act
		ratingServiceImpl.delete(rating);

		// Assert
		verify(ratingRepository, times(1)).delete(rating);
	}
}
