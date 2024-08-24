package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;

import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BidListServiceImplTest {

	@Mock  // test the service without depending on the database
	private BidListRepository bidListRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private BidListServiceImpl bidListServiceImpl;

	private BidList bidList;

	@BeforeEach // initializes a BidList before each test
	public void setUp() {
		bidList = new BidList();
		bidList.setBidListId(1);
		bidList.setAccount("Account Test");
		bidList.setType("Type Test");
		bidList.setBidQuantity(10.0);
	}

	// test Find All bidLists method
	@Test
	public void testFindAllBidLists() {
	    // Arrange
		List<BidList> bidLists = Arrays.asList(bidList, new BidList());
		when(bidListRepository.findAll()).thenReturn(bidLists);

	    // Act
		List<BidList> result = bidListServiceImpl.findAll();

	    // Assert
		verify(bidListRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save bidList method
	@Test
	public void testSaveBidList() {
	    // Arrange
		when(bidListRepository.save(bidList)).thenReturn(bidList);

	    // Act
		BidList result = bidListServiceImpl.save(bidList);

	    // Assert
		verify(bidListRepository, times(1)).save(bidList);
		assertEquals(bidList.getAccount(), result.getAccount());
		assertEquals(bidList.getType(), result.getType());
		assertEquals(bidList.getBidQuantity(), result.getBidQuantity());
	}

	// test find bidList By Id method
	@Test
	public void testFindBidListById() {
	    // Arrange
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

	    // Act
		Optional<BidList> result = bidListServiceImpl.findById(1);

	    // Assert
		verify(bidListRepository, times(1)).findById(1);
		assertTrue(result.isPresent());
		assertEquals(bidList.getAccount(), result.get().getAccount());
		assertEquals(bidList.getType(), result.get().getType());
		assertEquals(bidList.getBidQuantity(), result.get().getBidQuantity());
	}

	// test Delete a bidList method 
	@Test
	public void testDeleteBidList() {
	    // Arrange
		doNothing().when(bidListRepository).delete(bidList);

	    // Act
		bidListServiceImpl.delete(bidList);

	    // Assert
		verify(bidListRepository, times(1)).delete(bidList);
	}
}
