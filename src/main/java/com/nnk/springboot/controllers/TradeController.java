package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.services.TradeService;
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
public class TradeController { //Handles requests and redirects to given page

	// User service Injection
	@Autowired
	private UserService userService;

	// Trade Service Injection
	@Autowired
	private TradeService tradeService;

	// Show trade List Form with user authentication
	@GetMapping("/trade/list")
	public String showTradeList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("trades", tradeService.findAllTrades());
		return "trade/list";
	}

	// Show add trade Form
	@GetMapping("/trade/add")
	public String addTradeForm(Trade trade) {
		return "trade/add";
	}

	// Create trade if the fields are validated
	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "trade/add";
		}
		tradeService.save(trade);
		return "redirect:/trade/list";
	}

	// show update trade Form
	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade id" + id));
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	// Update trade by Id if the fields are validated
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			trade.setTradeId(id);
			return "trade/update";
		}
		tradeService.save(trade);
		return "redirect:/trade/list";
	}

	// Delete trade by Id
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade id" + id));
		tradeService.delete(trade);
		return "redirect:/trade/list";
	}
}
