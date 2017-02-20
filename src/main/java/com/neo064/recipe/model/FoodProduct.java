package com.neo064.recipe.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * Represents a Food Product (exemples : tomatoes, butter, etc.) defined by : -
 * a name - a density - a comment
 *
 * @author Neo
 *
 */
@Entity
public class FoodProduct extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2656418275777707361L;

	public static final String NAME_CONST = "name";

	private String name;

	/**
	 * g/ml or av.oz./fl.oz.
	 */
	private Float density;

	@Lob
	private String comment;

	/**
	 * Default constructor.
	 */
	public FoodProduct() {
		// Default constructor nothing to do
	}

	/**
	 * Constructor (used in daos).
	 *
	 * @param name
	 *            the food product name
	 */
	public FoodProduct(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Float getDensity() {
		return density;
	}

	public void setDensity(final Float density) {
		this.density = density;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
