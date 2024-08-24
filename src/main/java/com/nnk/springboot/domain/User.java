package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@Size(min = 2, max = 31)
	private String username;

	@Column(nullable = false)
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "Le mot de passe doit contenir au moins une lettre, un chiffre et une longueur minimale de 8 caractères")
	private String password;

	@Column(nullable = false)
	@Size(min = 2)
	private String fullname;

	@Column(nullable = false)
	@NotNull(message = "Sélectionner un  rôle")
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
