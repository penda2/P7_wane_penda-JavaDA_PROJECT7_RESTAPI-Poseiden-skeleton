package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class TradeServiceImplTest {

	@Mock // test the service without depending on the database
	private TradeRepository tradeRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private TradeServiceImpl tradeServiceImpl;

	private Trade trade;

	@BeforeEach // initializes a CurvePoint before each test
	public void setUp() {
		trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("Doe");
		trade.setType("older");
		trade.setBuyQuantity(5d);

	}

	// test Find All Trades method
	@Test
	public void testFindAllTrades() {
		// Arrange
		List<Trade> trades = Arrays.asList(trade, new Trade());
		when(tradeRepository.findAll()).thenReturn(trades);

		// Act
		List<Trade> result = tradeServiceImpl.findAllTrades();

		// Assert
		verify(tradeRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save Trade method
	@Test
	public void testSaveTrade() {
		// Arrange
		when(tradeRepository.save(trade)).thenReturn(trade);

		// Act
		Trade result = tradeServiceImpl.save(trade);

		// Assert
		verify(tradeRepository, times(1)).save(trade);
		assertEquals(trade.getAccount(), result.getAccount());
		assertEquals(trade.getBuyQuantity(), result.getBuyQuantity());
	}

	// test Find Trade By Id method
	@Test
	public void testFindTradeById() {
		// Arrange
		when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

		// Act
		Optional<Trade> result = tradeServiceImpl.findById(1);

		// Assert
		verify(tradeRepository, times(1)).findById(1);
		assertTrue(result.isPresent());
		assertEquals(trade.getType(), result.get().getType());
		assertEquals(trade.getAccount(), result.get().getAccount());
	}

	// test delete Trade method
	@Test
	public void testDeleteTrade() {
		// Arrange
		doNothing().when(tradeRepository).delete(trade);

		// Act
		tradeServiceImpl.delete(trade);

		// Assert
		verify(tradeRepository, times(1)).delete(trade);
	}
}
