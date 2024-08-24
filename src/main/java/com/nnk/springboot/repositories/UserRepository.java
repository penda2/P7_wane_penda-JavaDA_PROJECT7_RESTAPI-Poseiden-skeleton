package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	Optional<User> findByUsername(String username);
}
