package com.neo064.recipe.client.common.event;

import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.request.cycle.RequestCycle;

import com.google.common.collect.Lists;

/**
 * {@link AjaxEventBehavior} catching a Key Press event.
 *
 * @author Neo
 *
 */
public abstract class OnKeyPressAjaxEventBehavior extends AjaxEventBehavior {

	private static final String KEYCODE = "keycode";

	public static final String EVENT_KEY_PRESS = "keypress";

	private final List<Integer> keysToListen = Lists.newArrayList();

	private static final String JS_KEYCODE_VAR_DECLARATION = "var keycode = Wicket.Event.keyCode(attrs.event); if ({0}) return true; else return false;";

	/**
	 * Listens to all keys press, the event is triggered when a key is pressed.
	 */
	public OnKeyPressAjaxEventBehavior() {
		super(EVENT_KEY_PRESS);
	}

	/**
	 * Listens to specific keys press, the event is triggered when one of the
	 * keys is pressed.
	 *
	 * @param keysToListen
	 *            the list of keys to listen identified by their key codes.
	 */
	public OnKeyPressAjaxEventBehavior(final List<Integer> keysToListen) {
		this();
		this.keysToListen.addAll(keysToListen);
	}

	@Override
	protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		final IAjaxCallListener listener = new AjaxCallListener() {
			@Override
			public CharSequence getPrecondition(final Component component) {
				// this javascript code evaluates wether an ajaxcall is
				// necessary.
				// Here only by keyocdes for F9 and F10
				return MessageFormat.format(JS_KEYCODE_VAR_DECLARATION, getJSKeyCodeCondition());
			}
		};
		attributes.getAjaxCallListeners().add(listener);

		// Append the pressed keycode to the ajaxrequest
		attributes.getDynamicExtraParameters()
				.add("var eventKeycode = Wicket.Event.keyCode(attrs.event); return {keycode: eventKeycode};");

	}

	private String getJSKeyCodeCondition() {
		if (keysToListen.isEmpty()) {
			return Boolean.TRUE.toString();
		} else {
			StringBuilder condition = new StringBuilder();
			for (final Integer keyCodeValue : keysToListen) {
				condition.append('(').append(KEYCODE).append(" == ").append(keyCodeValue).append(") ").append("||");
			}
			final int lastIndexOf = condition.lastIndexOf("||");
			condition = condition.replace(lastIndexOf, lastIndexOf + 2, "");
			return condition.toString();
		}
	}

	@Override
	protected final void onEvent(final AjaxRequestTarget target) {
		onEvent(target, RequestCycle.get().getRequest().getRequestParameters().getParameterValue(KEYCODE).toInt());
	}

	/**
	 * Method triggered when pressing a key defined in the constructor.
	 *
	 * @param target
	 *            the {@link AjaxRequestTarget}
	 * @param keyPressed
	 *            the key pressed (cf. {@link KeyEvent}
	 */
	protected abstract void onEvent(final AjaxRequestTarget target, Integer keyPressed);

}
