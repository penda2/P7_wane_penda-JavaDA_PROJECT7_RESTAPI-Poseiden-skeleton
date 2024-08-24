package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeptions.CustomException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController { //Handles requests and redirects to given page
	
	// User Repository Injection
    @Autowired
    private UserRepository userRepository;
    
	// User service Injection
    @Autowired
    private UserService userService;

	// Show user List Form with user authentication
    @GetMapping("/user/list")
    public String showUserList(Model model, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new CustomException("Utilisateur introuvable."));
		model.addAttribute("user", user);
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }
    
	// Show add user Form
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

	// Create user if the fields are validated
    @PostMapping("/user/validate") // user/add
    public String validate(@Valid  @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add"; 
        }        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
    
	// show update user Form
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

	// Update user by Id if the fields are validated
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid  @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user); 
            return "user/update";
        }
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        existingUser.setUsername(user.getUsername());
        existingUser.setFullname(user.getFullname());
        existingUser.setRole(user.getRole());

        if (!user.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            existingUser.setPassword(encoder.encode(user.getPassword()));
        }
        userRepository.save(existingUser);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
    
	// Delete user by Id
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
