package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/*implementation of methods declared 
 * in the RuleNameService interface
 */

@Service
public class RuleNameServiceImpl implements RuleNameService {

	// Dependency injection 
	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Override
	public List<RuleName> findAllRuleNames() {
		return ruleNameRepository.findAll();
	}

	@Override
	public RuleName save(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	@Override
	public Optional<RuleName> findById(Integer id) {
		return ruleNameRepository.findById(id);
	}

	@Override
	public void delete(RuleName ruleName) {
		ruleNameRepository.delete(ruleName);
	}
}
