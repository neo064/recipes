package com.neo064.recipe.client.common.ui.field;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.DynamicImageResource;

/**
 * Displays the image linked to a model dynamically. If the image's model is not
 * set, a default image is displayed.
 *
 * @author Neo
 *
 */
public class DynamicImageField extends Image {

	private static final String DEFAULT_IMAGE_PATH = "/images/noimage.png";

	/**
	 * Instantiates the field.
	 * 
	 * @param id
	 *            the markup id
	 * @param model
	 *            the associated model
	 */
	public DynamicImageField(final String id, final IModel<?> model) {
		super(id, model);
		setOutputMarkupId(true);
		setImageResource();
	}

	/**
	 * Refresh image after updating the linked model.
	 */
	public void refreshImage() {
		setImageResource();
	}

	/**
	 * Displays the image or the default one if empty.
	 */
	private void setImageResource() {
		final byte[] photo = (byte[]) getDefaultModelObject();
		if (ArrayUtils.isEmpty(photo)) {
			setImageResource(new ContextRelativeResource(DEFAULT_IMAGE_PATH));
		} else {
			setImageResource(new DynamicImageResource() {

				@Override
				protected byte[] getImageData(final Attributes attributes) {
					return photo;
				}
			});
		}
	}
}
