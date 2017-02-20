package com.neo064.recipe.model;

/**
 * Defines an object as sortable, it will posess a number defining its rank.
 *
 * @author Neo
 *
 */
public interface ISortable {

	/**
	 *
	 * @return the rank.
	 */
	Byte getNumber();

	/**
	 * Sets the rank order.
	 *
	 * @param number
	 *            the new rank.
	 */
	void setNumber(Byte number);
}
