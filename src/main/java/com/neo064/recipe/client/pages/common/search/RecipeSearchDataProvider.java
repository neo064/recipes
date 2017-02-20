package com.neo064.recipe.client.pages.common.search;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.base.Optional;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;

/**
 * Provides data from the {@link Recipe} table.
 *
 * @author Neo
 *
 */
public class RecipeSearchDataProvider implements IDataProvider<Recipe> {

	private final IRecipeDao recipeDao;

	private final Optional<String> keyword;

	private final SearchMode searchMode;

	/**
	 * Constructor.
	 *
	 * @param recipeDao
	 *            the dao retrieving {@link Recipe} entities
	 * @param searchMode
	 *            the {@link SearchMode} : {@link SearchMode#ALL} = retrieve all
	 *            recipes {@link SearchMode#KEYWORD} = retrieve recipes matching
	 *            the keyword
	 *
	 * @param keyword
	 *            the keyword to reduce the search result
	 */
	public RecipeSearchDataProvider(final IRecipeDao recipeDao, final SearchMode searchMode,
			final Optional<String> keyword) {
		this.keyword = keyword;
		this.recipeDao = recipeDao;
		this.searchMode = searchMode;
	}

	@Override
	public void detach() {
		// Nothing to do on detach
	}

	@Override
	public Iterator<Recipe> iterator(final long first, final long count) {
		if (SearchMode.ALL.equals(searchMode)) {
			return recipeDao.findAllPaginatedRecipes(first, count).iterator();
		}
		return recipeDao.findByKeyword(keyword.get(), Optional.of(first), Optional.of(count)).iterator();
	}

	@Override
	public long size() {
		if (SearchMode.ALL.equals(searchMode)) {
			return recipeDao.findAllCount();
		}
		return recipeDao.nbRecordsByKeyword(keyword.get());
	}

	@Override
	public IModel<Recipe> model(final Recipe object) {
		return Model.<Recipe>of(object);
	}

}
