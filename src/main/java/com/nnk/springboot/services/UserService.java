package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.User;

public interface UserService {
	// Method declarations that will be implemented in UserServiceImpl class

	User saveUser(User user);

	List<User> findAllUsers();

	Optional<User> getCurrentUser();
	
    Optional<User> findByUsername(String username);

}
