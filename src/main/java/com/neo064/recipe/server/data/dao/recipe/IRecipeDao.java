package com.neo064.recipe.server.data.dao.recipe;

import java.util.List;

import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.common.ISearchAutoComplete;
import com.neo064.recipe.server.data.dao.common.ISearchByKeywordDao;

/**
 * Retrieves {@link Recipe} data.
 *
 * @author Neo
 *
 */
public interface IRecipeDao extends ISearchByKeywordDao<Recipe>, ISearchAutoComplete<Recipe> {

	/**
	 * Find all recipes (no filter) through a paginated table.
	 * 
	 * @param offset
	 *            the offset to search from
	 * @param maxResults
	 *            the number of results
	 * @return the list of {@link Recipe}
	 */
	List<Recipe> findAllPaginatedRecipes(Long offset, Long maxResults);
}
