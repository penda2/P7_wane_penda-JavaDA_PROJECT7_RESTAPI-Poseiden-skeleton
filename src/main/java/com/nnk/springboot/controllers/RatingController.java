package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.services.RatingService;
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
public class RatingController { //Handles requests and redirects to given page
	
	// Rating service Injection
	@Autowired
	private RatingService ratingService;
	
	// User service Injection
	@Autowired
	private UserService userService;
  
	// Show Rating List Form with user authentication
    @GetMapping("/rating/list")
	public String showRatingList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("ratings", ratingService.findAllRatings());
		return "rating/list";
	}
	
	// Show add Rating Form
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

	// Create Rating if the fields are validated
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
		}
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

	// show update Rating Form
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating id" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

	// Update rating by Id if the fields are validated
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
			rating.setId(id);
			return "rating/update";
		}
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

	// Delete rating by Id
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("invlid rating id" + id));
        ratingService.delete(rating);
        return "redirect:/rating/list";
    }
}

