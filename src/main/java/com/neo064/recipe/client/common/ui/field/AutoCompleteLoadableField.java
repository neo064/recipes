package com.neo064.recipe.client.common.ui.field;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import com.google.common.base.Optional;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.neo064.recipe.model.BaseEntity;
import com.neo064.recipe.server.data.dao.common.ISearchAutoComplete;

/**
 * {@link AutoCompleteTextField} displaying data from a table in the database
 *
 * @author Neo
 *
 * @param <T>
 *            an entity from the database
 */
public class AutoCompleteLoadableField<T extends BaseEntity> extends AutoCompleteTextField<T> {

	private static final Logger LOGGER = Logger.getLogger(AutoCompleteLoadableField.class);

	private final ISearchAutoComplete<T> dao;
	private final Optional<Class<T>> mainClass;
	private String oldInputValue;

	/**
	 * Instantiates the field.
	 *
	 * @param id
	 *            the markupid
	 * @param model
	 *            the associated model
	 * @param renderer
	 *            {@link ITextRenderer} to display the objects as Text
	 * @param dao
	 *            {@link ISearchAutoComplete} dao to get the data from the
	 *            database
	 * @param mainClass
	 *            the class of objects contained in the field
	 */
	public AutoCompleteLoadableField(final String id, final IModel<T> model, final ITextRenderer<T> renderer,
			final ISearchAutoComplete<T> dao, final Optional<Class<T>> mainClass) {
		super(id, model, renderer);
		setOutputMarkupId(true);
		this.dao = dao;
		this.mainClass = mainClass;
	}

	/**
	 * Instantiates the field.
	 *
	 * @param id
	 *            the markupid
	 * @param renderer
	 *            {@link ITextRenderer} to display the objects as Text
	 * @param dao
	 *            {@link ISearchAutoComplete} dao to get the data from the
	 *            database
	 * @param mainClass
	 *            the class of objects contained in the field
	 */
	public AutoCompleteLoadableField(final String id, final ITextRenderer<T> renderer, final ISearchAutoComplete<T> dao,
			final Optional<Class<T>> mainClass) {
		this(id, new Model<T>(), renderer, dao, mainClass);
	}

	@Override
	protected List<T> getChoices(final String input) {
		final List<T> findByKeyword = dao.findAutoComplete(input);
		if (findByKeyword.isEmpty()) {
			onEmpty(findByKeyword);
		}

		return findByKeyword;
	}

	/**
	 * Triggered after an empty search. It displays a text to add a new entry in
	 * the database.
	 * 
	 * @param list
	 *            the result list of the search to add the "empty entry"
	 */
	private void onEmpty(final List<T> list) {
		if (mainClass.isPresent()) {
			try {
				final T newInstance = mainClass.get().newInstance();
				final PropertyAccessor myAccessor = PropertyAccessorFactory.forDirectFieldAccess(newInstance);
				// set the property directly, bypassing the mutator (if any)
				myAccessor.setPropertyValue(getRenderer().getFields().get(0), "Entrée inexistante, la créer ?");
				newInstance.setId(-42L);
				list.add(newInstance);
			} catch (final InstantiationException e) {
				LOGGER.error("Impossible to instantiate the Autocomplete inner class for empty value", e);
			} catch (final IllegalAccessException e) {
				LOGGER.error(
						"Impossible to access the text displau metho in the Autocomplete inner class for empty value",
						e);
			}
		}
	}

	@Override
	protected void onModelChanging() {
		super.onModelChanging();
		oldInputValue = getInput();
	}

	@Override
	protected void onSelected(final AjaxRequestTarget target) {
		if (mainClass.isPresent() && getModelObject() != null && getModelObject().getId().equals(-42L)) {
			final Class<T> myClass = mainClass.get();
			Constructor<T> declaredConstructor;
			try {
				declaredConstructor = myClass.getDeclaredConstructor(String.class);
				declaredConstructor.setAccessible(true);
				T newInstance;
				newInstance = declaredConstructor.newInstance(oldInputValue);
				final T save = dao.save(newInstance);
				setModelObject(save);
				target.add(this);
			} catch (NoSuchMethodException | SecurityException e) {
				LOGGER.error(
						"Impossible to access the text displau metho in the Autocomplete inner class for empty value",
						e);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				LOGGER.error("Impossible to instantiate the Autocomplete inner class for empty value", e);
			}
		}
	}
}
