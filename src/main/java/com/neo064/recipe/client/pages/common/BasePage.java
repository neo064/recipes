package com.neo064.recipe.client.pages.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.neo064.recipe.client.common.event.OnKeyPressAjaxEventBehavior;
import com.neo064.recipe.client.common.ui.field.AutoCompleteLoadableField;
import com.neo064.recipe.client.common.utils.CommonConst;
import com.neo064.recipe.client.common.utils.KeyCodeConst;
import com.neo064.recipe.client.pages.common.search.SearchMode;
import com.neo064.recipe.client.pages.common.search.SearchPage;
import com.neo064.recipe.client.pages.recipe.add.AddRecipePage;
import com.neo064.recipe.client.pages.recipe.view.ViewRecipePage;
import com.neo064.recipe.client.pages.tips.TipsPage;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;

/**
 * Main class for all the application pages. It contains the header and the menu
 * inherited by all pages
 *
 * @author Neo
 *
 */
public abstract class BasePage extends WebPage {

	private static final long serialVersionUID = -4197908947915172889L;

	@SpringBean
	private IRecipeDao recipeDao;

	/**
	 * Basic instantiation.
	 */
	public BasePage() {
		super();
		initMenu();
		initPage();
	}

	/**
	 * Instantiation when called with {@link PageParameters}.
	 *
	 * @param parameters
	 *            the http parameters passed
	 */
	public BasePage(final PageParameters parameters) {
		super(parameters);
		initMenu();
		initPage();
	}

	private void initPage() {
		final AutoCompleteLoadableField<Recipe> searchField = new AutoCompleteLoadableField<Recipe>("search",
				new TextRenderer<Recipe>(Recipe.TITLE_CONST), recipeDao, Optional.absent()) {
			@Override
			protected void onSelected(final AjaxRequestTarget target) {
				final PageParameters params = new PageParameters();
				params.add(CommonConst.PARAM_RECIPE, getModelObject().getId());
				setResponsePage(ViewRecipePage.class, params);
			}
		};
		searchField.add(new OnKeyPressAjaxEventBehavior(Lists.newArrayList(KeyCodeConst.ENTER)) {

			@Override
			protected void onEvent(final AjaxRequestTarget target, final Integer keyPressed) {
				if (StringUtils.isNotEmpty(searchField.getInput())) {
					final PageParameters params = new PageParameters();
					params.add(CommonConst.PARAM_SEARCH_MODE, SearchMode.KEYWORD);
					params.add(CommonConst.PARAM_SEARCH_KEYWORD, searchField.getInput());
					setResponsePage(SearchPage.class, params);
				}
			}
		});

		add(searchField);
	}

	private void initMenu() {
		add(new Link<String>("recipesmenu") {

			@Override
			public void onClick() {
				setResponsePage(SearchPage.class,
						new PageParameters().add(CommonConst.PARAM_SEARCH_MODE, SearchMode.ALL));
			}
		});
		add(new Link<String>("tipsmenu") {

			@Override
			public void onClick() {
				setResponsePage(new TipsPage());
			}

		});
		add(new Link<String>("addmenu") {

			@Override
			public void onClick() {
				setResponsePage(new AddRecipePage());
			}

		});
	}

}