package com.neo064.recipe.client.common.ui.field.image;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.neo064.recipe.client.common.ui.field.DynamicImageField;

/**
 * Custom image panel containing a {@link DynamicImageField}.
 *
 * @author Neo
 *
 */
class ImageModalWindowPanel extends Panel {

	private static final String IMAGE_ID = "image";

	/**
	 * Intantiates the panel.
	 *
	 * @param id
	 *            the markup id
	 * @param model
	 *            the associated model
	 */
	public ImageModalWindowPanel(final String id, final IModel<?> model) {
		super(id, model);
		add(new DynamicImageField(IMAGE_ID, model));
	}
}
