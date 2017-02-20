package com.neo064.recipe.client.common.ui.field;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import com.google.common.base.Optional;
import com.googlecode.wicket.jquery.ui.form.dropdown.AjaxDropDownChoice;
import com.neo064.recipe.model.BaseEntity;
import com.neo064.recipe.server.data.dao.common.IGenericDao;

/**
 * Combobox displaying all values from a table in the database.
 *
 * @author Neo
 *
 * @param <T>
 *            the Entity contained in the combobox
 */
public class LoadableComboBoxField<T extends BaseEntity> extends AjaxDropDownChoice<T> {

	private final Optional<String> defaultDisplayValue;

	/**
	 * Instantiates the combobox.
	 *
	 * @param id
	 *            the markup id
	 * @param renderer
	 *            the renderer displaying as texts the combo values
	 * @param dao
	 *            the dao retrieving all values from the database
	 * @param defaultDisplayValue
	 *            the default value if null (optional)
	 */
	public LoadableComboBoxField(final String id, final IChoiceRenderer<? super T> renderer, final IGenericDao<T> dao,
			final Optional<String> defaultDisplayValue) {
		super(id);
		this.defaultDisplayValue = defaultDisplayValue;
		setOutputMarkupId(true);
		setChoiceRenderer(renderer);
		setChoices(dao.findAll());
		add(new AjaxFormComponentUpdatingBehavior("click") {

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				setChoices(dao.findAll());
			}
		});
	}

	@Override
	protected String getNullKeyDisplayValue() {
		if (defaultDisplayValue.isPresent()) {
			return defaultDisplayValue.get();
		}
		return super.getNullValidDisplayValue();
	}
}
