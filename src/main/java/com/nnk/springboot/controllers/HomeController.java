package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController { // Handles requests and redirects to given pages

	// Shows home page
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}

	// Shows administrator home page (bidList/list)
	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}
}
