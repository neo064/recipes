package com.neo064.recipe.server.data.dao.common;

import java.util.List;

import com.google.common.base.Optional;
import com.neo064.recipe.model.BaseEntity;

/**
 * Implemented by entities retrievable through a keyword and displayed in a HTML
 * table.
 *
 * @author Neo
 *
 * @param <T>
 *            the type of the entity
 */
public interface ISearchByKeywordDao<T extends BaseEntity> extends IGenericDao<T> {

	/**
	 * Finds entities matching a given keyword.
	 *
	 * @param keyword
	 *            the keyword used for the search.
	 * @param offset
	 *            the offset to search from in the database
	 * @param maxResults
	 *            the number of entities to retrieve
	 * @return the list of matching entities
	 */
	public List<T> findByKeyword(String keyword, Optional<Long> offset, Optional<Long> maxResults);

	/**
	 *
	 * @param keyword
	 *            the keyword used for the search.
	 * @return the number of entities matching the keyword
	 */
	public Long nbRecordsByKeyword(String keyword);
}
