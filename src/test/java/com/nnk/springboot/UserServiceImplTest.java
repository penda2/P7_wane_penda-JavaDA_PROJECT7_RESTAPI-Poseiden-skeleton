package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock // test the service without depending on the database
	private UserRepository userRepository;

	@InjectMocks // injects the mocked dependencies into the service to be tested
	private UserServiceImpl userServiceImpl;

	private User user;

	// Mock the SecurityContext and Authentication
	@Mock
	private Authentication authentication;

	@Mock
	private SecurityContext securityContext;

	@BeforeEach // initializes a CurvePoint before each test
	public void setUp() {
		user = new User();
		user.setId(1);
		user.setFullname("Admin");
		user.setUsername("John");
		user.setPassword("John20!");
		user.setRole("ADMIN");
	}

	// test Find All Users method
	@Test
	public void testFindAllUsers() {
		// Arrange
		List<User> users = Arrays.asList(user, new User());
		when(userRepository.findAll()).thenReturn(users);

		// Act
		List<User> result = userServiceImpl.findAllUsers();

		// Assert
		verify(userRepository, times(1)).findAll();
		assertEquals(2, result.size());
	}

	// test save User method
	@Test
	public void testSaveUser() {
		// Arrange
		when(userRepository.save(user)).thenReturn(user);

		// Act
		User result = userServiceImpl.saveUser(user);

		// Assert
		verify(userRepository, times(1)).save(user);
		assertEquals(user.getFullname(), result.getFullname());
		assertEquals(user.getRole(), result.getRole());
	}

	// test Find User By User Name method
	@Test
	public void testFindUserByUserName() {
		// Arrange
		when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));

		// Act
		Optional<User> result = userServiceImpl.findByUsername("John");

		// Assert
		verify(userRepository, times(1)).findByUsername("John");
		assertTrue(result.isPresent());
		assertEquals(user.getFullname(), result.get().getFullname());
		assertEquals(user.getRole(), result.get().getRole());
	}

	// test Get Current User method
	@Test
	public void testGetCurrentUser() {
		// Arrange
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		when(authentication.getName()).thenReturn("testUser");
		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

		// Act
		Optional<User> result = userServiceImpl.getCurrentUser();

		// Assert
		verify(userRepository, times(1)).findByUsername("testUser");
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
	}
}
