package com.neo064;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;

import com.google.common.collect.Lists;
import com.neo064.recipe.WicketApplication;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;
import com.neo064.recipe.server.data.dao.recipe.RecipeDao;

public class WicketTestApplication extends WicketApplication {
	private final ApplicationContextMock ctx = new ApplicationContextMock();

	public ApplicationContextMock getCtx() {
		return ctx;
	}

	@Override
	public void init() {
		final IRecipeDao dao = new RecipeDao() {
			@Override
			public Long findAllCount() {
				return 7L;
			}

			@Override
			public List<Recipe> findAllPaginatedRecipes(final Long offset, final Long maxResults) {
				return Lists.newArrayList();
			}
		};
		ctx.putBean(dao);
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
	}
}
