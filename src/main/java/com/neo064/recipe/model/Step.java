package com.neo064.recipe.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Represents a recipe step defined by: </br>
 * - the associated recipe</br>
 * - its rank number</br>
 * - its title</br>
 * - a description</br>
 * 
 * @author Neo
 *
 */
@Entity
public class Step extends BaseEntity implements Serializable, ISortable {

	private static final long serialVersionUID = 6936949803553196559L;

	public static final String NUMBER_CONST = "number";
	public static final String DESCRIPTION_CONST = "description";

	@ManyToOne
	private Recipe recipe;

	private Byte number;

	private String title;

	@Lob
	private String description;

	/**
	 * Default constructor.
	 */
	public Step() {
		// Default constructor nothing to do
	}

	/**
	 * Constructor (used in daos).
	 *
	 * @param recipe
	 *            the associated recipe
	 */
	public Step(final Recipe recipe) {
		this.recipe = recipe;
	}

	public String getDescription() {
		return description;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(final Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public Byte getNumber() {
		return number;
	}

	@Override
	public void setNumber(final Byte number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
