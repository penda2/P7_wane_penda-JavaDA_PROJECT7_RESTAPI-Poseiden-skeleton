package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig { // application security configuration

	// Dependency injection
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/*
	 * Configuration of filter chains for access to public and restricted pages of
	 * the application
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/login", "/error", "/css/**").permitAll();
			auth.requestMatchers("/", "/user/list").hasAnyRole("ADMIN", "USER");
			auth.requestMatchers("/**").hasRole("ADMIN");
			auth.anyRequest().authenticated();
		}).formLogin(form -> form.loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/", true)
				.usernameParameter("username").passwordParameter("password").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
						.deleteCookies("JSESSIONID") // Deleting session cookie after logout
						.invalidateHttpSession(true).permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Create session
						.invalidSessionUrl("/login?invalid") // Redirect to this URL if session is invalid
						.maximumSessions(1) // Limits the number of simultaneous sessions for a user
						.expiredUrl("/login?expired")); // Redirects to this URL if session has expired
		return http.build();
	}


	// Added to encrypt passwords with BCrypt algorithm
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * handling authentication requests using the CustomUserDetailsService to load
	 * user information and the BCryptPasswordEncoder to encrypt passwords
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}
}
