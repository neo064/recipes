package com.neo064.recipe.client.common.ui;

/**
 * Describe a component that has different behaviors depending on the
 * {@link FormMode}.
 *
 * @author Neo
 *
 */
public interface FormModeDependable {

	/**
	 *
	 * @return the current {@link FormMode}.
	 */
	FormMode getFormMode();

	/**
	 * Sets the current {@link FormMode}.
	 * 
	 * @param formMode
	 *            the mode to set
	 */
	void setFormMode(FormMode formMode);
}
