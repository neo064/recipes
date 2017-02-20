package com.neo064.recipe.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.google.common.collect.Lists;

/**
 * Represents a recipe defined by: <br/>
 * - a title</br>
 * - a rating</br>
 * - a list of steps</br>
 * - a list of ingredients</br>
 * - a photo</br>
 * - a comment</br>
 *
 *
 * @author Neo
 *
 */
@Entity
public class Recipe extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5026876596247398774L;

	public static final String TITLE_CONST = "title";
	public static final String STEPS_CONST = "steps";
	public static final String INGREDIENTS_CONST = "ingredients";
	public static final String PHOTO_CONST = "photo";
	public static final String PREPARATION_TIME = "preparationTime";
	public static final String COOKING_TIME = "cookingTime";
	public static final String VIDEO_URL = "videoURL";

	private String title;

	private Short preparationTime;

	private Short cookingTime;

	private String videoURL;

	@OrderBy("number ASC")
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Step> steps = Lists.newArrayList();

	@OrderBy("number ASC")
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ingredient> ingredients = Lists.newArrayList();

	private byte[] photo;

	@Lob
	private String comment;

	/**
	 * Default constructor.
	 */
	public Recipe() {
		// Default constructor nothing to do
	}

	/**
	 * Constructor (used in daos).
	 *
	 * @param id
	 *            the entity id.
	 * @param title
	 *            the recipe title.
	 */
	public Recipe(final Long id, final String title) {
		super();
		setId(id);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Short getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(final Short preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Short getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(final Short cookingTime) {
		this.cookingTime = cookingTime;
	}

	/**
	 * Adds the given step to the steps list.
	 *
	 * @param step
	 *            the step to add.
	 */
	public void addStep(final Step step) {
		step.setRecipe(this);
		steps.add(step);
	}

	/**
	 * Removes the given step from the steps list.
	 *
	 * @param step
	 *            the step to remove.
	 */
	public void removeStep(final Step step) {
		steps.remove(step);
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(final List<Step> steps) {
		this.steps = steps;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(final List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(final byte[] photo) {
		this.photo = photo;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(final String videoURL) {
		this.videoURL = videoURL;
	}
}
