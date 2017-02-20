package com.neo064.recipe.client.common.ui.field.image;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;

import com.google.common.collect.Lists;
import com.neo064.recipe.client.common.event.OnKeyPressAjaxEventBehavior;
import com.neo064.recipe.client.common.utils.KeyCodeConst;

/**
 * Modal Window that displays an image.
 *
 * @author Neo
 *
 */
class ImageModalWindow extends ModalWindow {

	/**
	 * Constructor.
	 *
	 * @param id
	 *            the component id
	 * @param model
	 *            the image to display model
	 * @param parent
	 *            the parent component of the modal window (usually the
	 *            {@link WebPage}).
	 */
	public ImageModalWindow(final String id, final IModel<?> model, final MarkupContainer parent) {
		super(id);
		setUseInitialHeight(true);
		setResizable(false);
		final ClientProperties properties = ((WebClientInfo) Session.get().getClientInfo()).getProperties();
		setUseInitialHeight(true);
		setInitialHeight(properties.getBrowserHeight() * 2 / 3);
		setInitialWidth(properties.getBrowserWidth() * 2 / 3);
		setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		setContent(new ImageModalWindowPanel(getContentId(), model));
		showUnloadConfirmation(false);
		parent.add(new OnKeyPressAjaxEventBehavior(Lists.newArrayList(KeyCodeConst.ESCAPE)) {

			@Override
			protected void onEvent(final AjaxRequestTarget target, final Integer keyPressed) {
				if (isShown()) {
					close(target);
				}
			}
		});
	}
}
