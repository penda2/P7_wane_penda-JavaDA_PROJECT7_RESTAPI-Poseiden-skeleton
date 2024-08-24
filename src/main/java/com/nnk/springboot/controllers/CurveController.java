package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.services.CurveService;
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
public class CurveController { // Handles requests and redirects to given pages

	// Injected User Service
	@Autowired
	private UserService userService;

	// Injected Curve point Service
	@Autowired
	private CurveService curveService;

	// Show Curve List Form with user authentication
	@GetMapping("/curvePoint/list")
	public String showCurveList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
		model.addAttribute("curvePoints", curveService.findAllCurvePoints());
		return "curvePoint/list";
	}

	// Show Curve point add Form
	@GetMapping("/curvePoint/add")
	public String addCurvePointForm(CurvePoint bid) {
		return "curvePoint/add";
	}

	// Create Curve point if the fields are validated
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}
		curveService.save(curvePoint);
		return "redirect:/curvePoint/list";
	}

	// show update curve point Form
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curveService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	// Update Curve Point by Id if the fields are validated
	@PostMapping("/curvePoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			curvePoint.setId(id);
			return "curvePoint/update";
		}
		curveService.save(curvePoint);
		return "redirect:/curvePoint/list";
	}

	// Delete curve Point by Id
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curveService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		curveService.delete(curvePoint);
		return "redirect:/curvePoint/list";
	}
}
