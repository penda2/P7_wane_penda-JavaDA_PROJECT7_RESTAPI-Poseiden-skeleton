package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController { // Handles requests and redirects to given pages

	// Injected User Repository
	@Autowired
	private UserRepository userRepository;

	// Shows login form
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	// Show users list page
	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userRepository.findAll());
		mav.setViewName("user/list");
		return mav;
	}

	// shows error page
	@GetMapping("/error")
	public ModelAndView accessDenied(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String errorMsg = "You are not authorized for the requested data.";

		if (authentication != null) {
			String username = authentication.getName();
			System.out.println("Username from Authentication: " + username);
			mav.addObject("username", username);
		} else {
			System.out.println("Authentication is null");
			mav.addObject("username", "Anonymous");
		}

		mav.addObject("errorMsg", errorMsg);
		mav.setViewName("error");
		return mav;
	}
}
