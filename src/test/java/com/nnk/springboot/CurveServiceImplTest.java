package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveServiceImpl;

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
public class CurveServiceImplTest {

	@Mock // test the service without depending on the database
	private CurvePointRepository curvePointRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private CurveServiceImpl curveServiceImpl;

	private CurvePoint curvePoint;

	@BeforeEach // initializes a CurvePoint before each test
	public void setUp() {
		curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setTerm(10d);
		curvePoint.setValue(5d);
	}

	// test Find All curvePoints method
	@Test
	public void testFindAllCurvePoints() {
		// Arrange
		List<CurvePoint> curvePoints = Arrays.asList(curvePoint, new CurvePoint());
		when(curvePointRepository.findAll()).thenReturn(curvePoints);

		// Act
		List<CurvePoint> result = curveServiceImpl.findAllCurvePoints();

		// Assert
		verify(curvePointRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save CurvePoint method
	@Test
	public void testSaveCurvePoint() {
		// Arrange
		when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

		// Act
		CurvePoint result = curveServiceImpl.save(curvePoint);

		// Assert
		verify(curvePointRepository, times(1)).save(curvePoint);
		assertEquals(curvePoint.getTerm(), result.getTerm());
		assertEquals(curvePoint.getValue(), result.getValue());
	}

	// test find CurvePoint By Id method
	@Test
	public void testFindCurvePointById() {
		// Arrange
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

		// Act
		Optional<CurvePoint> result = curveServiceImpl.findById(1);

		// Assert
		verify(curvePointRepository, times(1)).findById(1);
		assertTrue(result.isPresent());
		assertEquals(curvePoint.getTerm(), result.get().getTerm());
		assertEquals(curvePoint.getValue(), result.get().getValue());
	}

	// test Delete a CurvePoint method
	@Test
	public void testDeleteCurvePoint() {
		// Arrange
		doNothing().when(curvePointRepository).delete(curvePoint);

		// Act
		curveServiceImpl.delete(curvePoint);

		// Assert
		verify(curvePointRepository, times(1)).delete(curvePoint);
	}
}
