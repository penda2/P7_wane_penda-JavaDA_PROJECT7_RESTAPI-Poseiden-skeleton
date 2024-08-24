package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RuleNameController { //Handles requests and redirects to given page

	// User service Injection
	@Autowired
	private UserService userService;

	// RuleName Service Injection
	@Autowired
	private RuleNameService ruleNameService;

	// Show ruleName List Form with user authentication
	@GetMapping("/ruleName/list")
	public String showRuleList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
		return "ruleName/list";
	}

	// Show add ruleName Form
	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName) {
		return "ruleName/add";
	}

	// Create ruleName if the fields are validated
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ruleName/add";
		}
		ruleNameService.save(ruleName);
		return "redirect:/ruleName/list";
	}

	// show update ruleName Form
	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Rule Name Id" + id));
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	// Update ruleName by Id if the fields are validated
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			ruleName.setId(id);
			return "ruleName/update";
		}
		ruleNameService.save(ruleName);
		return "redirect:/ruleName/list";
	}

	// Delete ruleName by Id
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rule name id" + id));
		ruleNameService.delete(ruleName);
		return "redirect:/ruleName/list";
	}
}
