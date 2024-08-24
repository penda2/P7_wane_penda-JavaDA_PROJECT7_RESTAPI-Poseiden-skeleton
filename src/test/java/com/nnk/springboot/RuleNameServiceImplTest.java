package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;

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
public class RuleNameServiceImplTest {

	@Mock // test the service without depending on the database
	private RuleNameRepository ruleNameRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private RuleNameServiceImpl ruleNameServiceImpl;

	private RuleName ruleName;

	@BeforeEach // initializes a CurvePoint before each test
	public void setUp() {
		ruleName = new RuleName();
		ruleName.setId(1);
		ruleName.setDescription("Testing");
		ruleName.setName("Big");
		ruleName.setJson("Json");
		ruleName.setSqlStr("Sql Str");
		ruleName.setSqlPart("Sql Part");
		ruleName.setTemplate("Template");
	}

	// test Find All RuleNames method
	@Test
	public void testFindAllRuleNames() {
		// Arrange
		List<RuleName> ruleNames = Arrays.asList(ruleName, new RuleName());
		when(ruleNameRepository.findAll()).thenReturn(ruleNames);

		// Act
		List<RuleName> result = ruleNameServiceImpl.findAllRuleNames();

		// Assert
		verify(ruleNameRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save RuleName method
	@Test
	public void testSaveRuleName() {
		// Arrange
		when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

		// Act
		RuleName result = ruleNameServiceImpl.save(ruleName);

		// Assert
		verify(ruleNameRepository, times(1)).save(ruleName);
		assertEquals(ruleName.getDescription(), result.getDescription());
		assertEquals(ruleName.getTemplate(), result.getTemplate());
	}

	// test find RuleName By Id method
	@Test
	public void testFindRuleNameById() {
		// Arrange
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

		// Act
		Optional<RuleName> result = ruleNameServiceImpl.findById(1);

		// Assert
		verify(ruleNameRepository, times(1)).findById(1);
		assertTrue(result.isPresent());
		assertEquals(ruleName.getSqlPart(), result.get().getSqlPart());
		assertEquals(ruleName.getSqlStr(), result.get().getSqlStr());
	}

	// test Delete a RuleName method
	@Test
	public void testDeleteRuleName() {
		// Arrange
		doNothing().when(ruleNameRepository).delete(ruleName);

		// Act
		ruleNameServiceImpl.delete(ruleName);

		// Assert
		verify(ruleNameRepository, times(1)).delete(ruleName);
	}
}
