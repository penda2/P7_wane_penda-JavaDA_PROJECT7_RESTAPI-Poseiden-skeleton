package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rating")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	
	@NotNull(message = "Veuillez remplir ce champs")
    @Size(min = 1, max = 50, message = "La longueur doit être entre 1 et 50 caractères")
    @Column(nullable = false)
	String moodysRating;
	
	@NotNull(message = "Veuillez remplir ce champs")
    @Size(min = 1, max = 50, message = "La longueur doit être entre 1 et 50 caractères")
    @Column(nullable = false)
	String sandPRating;
	
	@NotNull(message = "Veuillez remplir ce champs")
    @Size(min = 1, max = 50, message = "La longueur doit être entre 1 et 50 caractères")
    @Column(nullable = false)
	String fitchRating;
	
	@NotNull(message = "Veuillez remplir ce champs")
    @Min(value = 1, message = "La valeur doit être positive")
    @Column(nullable = false)
	Integer orderNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
