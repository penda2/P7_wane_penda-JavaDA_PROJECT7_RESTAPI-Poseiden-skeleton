package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.RuleName;

public interface RuleNameService {
	// Method declarations that will be implemented in RuleNameServiceImpl class

	List<RuleName> findAllRuleNames();

	RuleName save(RuleName ruleName);

	Optional<RuleName> findById(Integer id);

	void delete(RuleName ruleName);
}
