package com.neo064.recipe.server.data.dao.common;

import java.util.List;

import com.neo064.recipe.client.common.ui.field.AutoCompleteLoadableField;
import com.neo064.recipe.model.BaseEntity;

/**
 * Implemented by entities retrievable through an
 * {@link AutoCompleteLoadableField}.
 *
 * @author Neo
 *
 * @param <T>
 *            the type of the entity
 */
public interface ISearchAutoComplete<T extends BaseEntity> extends IGenericDao<T> {

	/**
	 * Finds entities matching a given keyword.
	 *
	 * @param keyword
	 *            the keyword used for the search.
	 * @return the {@link List} of entities matching the keyword.
	 */
	public List<T> findAutoComplete(final String keyword);
}
