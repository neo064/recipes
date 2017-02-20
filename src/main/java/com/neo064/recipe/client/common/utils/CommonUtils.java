package com.neo064.recipe.client.common.utils;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

/**
 * Class utils used in the whole application.
 *
 * @author Neo
 *
 */
public final class CommonUtils {

	/**
	 * Class utils : private constructor.
	 */
	private CommonUtils() {

	}

	/**
	 * Trick to force the model update...
	 *
	 * @param component
	 *            the component linked to the model
	 * @return the component itself
	 */
	public static final <T extends Component> T addChangeEvent(final T component) {
		component.add(new AjaxFormComponentUpdatingBehavior("change") {

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				target.add(component);

			}
		});
		return component;
	}
}
