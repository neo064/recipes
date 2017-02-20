package com.neo064.recipe.client.common.ui.time;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.PropertyModel;

import com.neo064.recipe.client.common.ui.FormMode;
import com.neo064.recipe.client.common.ui.FormModeDependable;
import com.neo064.recipe.client.common.utils.CommonUtils;

/**
 * Class representing a time field. The time field is composed of :</br>
 * - an hour number field</br>
 * - a minute number field</br>
 *
 * When in {@link FormMode#VIEW} the mentioned fields are represented as
 * {@link Label}.
 *
 * @author Neo
 *
 */
public class TimeField extends FormComponentPanel<Short> implements FormModeDependable {

	private final NumberTextField<Short> hourField;
	private final NumberTextField<Short> minuteField;
	private final Label hourLabel;
	private final Label minuteLabel;

	private short hours;
	private short minutes;
	private FormMode formMode;

	/**
	 * Constructor.
	 *
	 * @param id
	 *            the markup id
	 * @param label
	 *            the field label
	 * @param formMode
	 *            the {@link FormMode} (view or edit)
	 */
	public TimeField(final String id, final String label, final FormMode formMode) {
		super(id);
		hourField = CommonUtils
				.addChangeEvent(new NumberTextField<Short>("hourfield", new PropertyModel<>(this, "hours"))
						.setMinimum((short) 0).setMaximum((short) 100));
		minuteField = CommonUtils
				.addChangeEvent(new NumberTextField<Short>("minutefield", new PropertyModel<>(this, "minutes"))
						.setMinimum((short) 0).setMaximum((short) 6000));
		hourLabel = new Label("hourlabel", new PropertyModel<>(this, "hours"));
		minuteLabel = new Label("minutelabel", new PropertyModel<>(this, "minutes"));
		this.formMode = formMode;
		init(label);
		onFormModeChanged();
	}

	private void init(final String label) {
		add(new Label("timefieldlabel", label));
		add(hourField);
		add(hourLabel);
		add(new Label("timehourlabel", "h"));
		add(minuteField);
		add(minuteLabel);
		add(new Label("timeminuteslabel", "min"));
	}

	private void onFormModeChanged() {
		if (FormMode.EDIT.equals(formMode)) {
			hourField.setVisible(true);
			minuteField.setVisible(true);
			hourLabel.setVisible(false);
			minuteLabel.setVisible(false);
		} else {
			hourField.setVisible(false);
			minuteField.setVisible(false);
			hourLabel.setVisible(true);
			minuteLabel.setVisible(true);
		}
	}

	@Override
	public void convertInput() {
		setConvertedInput((short) (hours * 60 + minutes));
	}

	@Override
	protected void onBeforeRender() {
		final Short value = getModelObject();
		if (value == null) {
			hours = 0;
			minutes = 0;
		} else {
			hours = (short) (value / 60);
			minutes = (short) (value % 60);
		}
		super.onBeforeRender();
	}

	@Override
	public FormMode getFormMode() {
		return formMode;
	}

	@Override
	public void setFormMode(final FormMode formMode) {
		this.formMode = formMode;
		onFormModeChanged();
	}
}
