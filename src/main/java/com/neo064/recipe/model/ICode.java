package com.neo064.recipe.model;

/**
 * Implemented by entity containing a unique identifier as a code (String).
 *
 * @author Neo
 *
 */
public interface ICode {

	/**
	 *
	 * @return the unique code associated.
	 */
	public String getCode();

	/**
	 * Sets the unique associated code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(String code);
}
