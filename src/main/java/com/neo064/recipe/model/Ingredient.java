package com.neo064.recipe.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

/**
 * Represents a {@link Recipe} ingredient (exemples : 3 tomatoes) defined by:
 * <br/>
 * - an associated recipe <br/>
 * - a food product <br/>
 * - a unit of quantity <br/>
 * - a quantity
 *
 * @author Neo
 *
 */
@Entity
public class Ingredient extends BaseEntity implements Serializable, ISortable {

	private static final long serialVersionUID = 7383324234592251163L;

	public static final String QUANTITY_CONST = "quantity";
	public static final String UNIT_CONST = "unit";
	public static final String FOOD_PRODUCT_CONST = "foodProduct";

	@ManyToOne
	private Recipe recipe;

	@ManyToOne
	private FoodProduct foodProduct;

	@ManyToOne
	@OrderBy("abbreviation ASC")
	private Unit unit;

	private Float quantity;

	private Byte number;

	public FoodProduct getFoodProduct() {
		return foodProduct;
	}

	public void setFoodProduct(final FoodProduct foodProduct) {
		this.foodProduct = foodProduct;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(final Float quantity) {
		this.quantity = quantity;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(final Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public Byte getNumber() {
		return this.number;
	}

	@Override
	public void setNumber(final Byte number) {
		this.number = number;
	}

}
