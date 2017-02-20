package com.neo064.recipe.client.common.ui.field.image;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.neo064.recipe.client.common.ui.field.DynamicImageField;

/**
 * Panel displaying an image and showing a {@link ModalWindow} on click on the
 * image.
 *
 * @author Neo
 *
 */
public class ImagePanel extends Panel {

	private final DynamicImageField imageField;

	private static final String IMAGE_ID = "image";
	private static final String IMAGE_MODAL_ID = "imagemodal";

	/**
	 * Constructor.
	 *
	 * @param id
	 *            the component id.
	 * @param model
	 *            the image {@link Model}
	 * @param parent
	 *            the parent component containing the image
	 */
	public ImagePanel(final String id, final IModel<?> model, final MarkupContainer parent) {
		super(id, model);
		imageField = new DynamicImageField(IMAGE_ID, model);
		final ImageModalWindow window = new ImageModalWindow(IMAGE_MODAL_ID, model, parent);
		imageField.add(new AjaxEventBehavior("click") {

			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				window.show(target);
			}
		});
		add(imageField);
		add(window);
	}

	/**
	 * Force the image refresh.
	 */
	public void refresh() {
		imageField.refreshImage();
	}
}
