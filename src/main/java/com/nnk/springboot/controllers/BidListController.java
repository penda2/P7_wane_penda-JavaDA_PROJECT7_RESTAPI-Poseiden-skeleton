package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {  //Handles requests and redirects to given pages

	// Injected Bid service
	@Autowired
	private BidListService bidListService;

	// Injected User service
	@Autowired
	private UserService userService;

	// Show Bid List Form with user authentication
	@GetMapping("/bidList/list")
	public String showBidList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("bidLists", bidListService.findAll());
		return "bidList/list";
	}

	// Show add Bid Form
	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	// Create bid if the fields are validated
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "bidList/add";
		}
		bidListService.save(bid);
		return "redirect:/bidList/list";
	}

	// show update bid Form
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	// Update Bid by Id if the fields are validated
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			bidList.setBidListId(id);
			return "bidList/update";
		}
		bidListService.save(bidList);
		return "redirect:/bidList/list";
	}

	// Delete Bid by Id
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		bidListService.delete(bidList);
		return "redirect:/bidList/list";
	}
}
