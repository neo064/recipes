package com.neo064.recipe.client.pages.common.search;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.neo064.recipe.client.common.ui.field.image.ImagePanel;
import com.neo064.recipe.client.common.utils.CommonConst;
import com.neo064.recipe.client.pages.common.BasePage;
import com.neo064.recipe.client.pages.recipe.view.ViewRecipePage;
import com.neo064.recipe.model.Ingredient;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;

/**
 * Page displaying a list of recipes.
 *
 * @author Neo
 *
 */
public class SearchPage extends BasePage {

	@SpringBean
	private IRecipeDao recipeDao;
	private final Optional<String> keyword;
	private final SearchMode searchMode;

	/**
	 * Constructor called when passing http {@link PageParameters}. They
	 * contains information to trigger a search.
	 *
	 * @param params
	 *            the {@link PageParameters}
	 */
	public SearchPage(final PageParameters params) {
		super(params);
		final StringValue keywordParam = params.get(CommonConst.PARAM_SEARCH_KEYWORD);
		keyword = keywordParam.isNull() ? Optional.absent() : Optional.of(keywordParam.toString());
		final StringValue searcheModeParam = params.get(CommonConst.PARAM_SEARCH_MODE);
		searchMode = !searcheModeParam.isNull() ? SearchMode.valueOf(searcheModeParam.toString()) : SearchMode.ALL;

		initDataView();
	}

	/**
	 * Default constructor, called when no page parameters. Call a full search
	 * without keywords.
	 */
	public SearchPage() {
		super();
		keyword = Optional.absent();
		searchMode = SearchMode.ALL;
		initDataView();
	}

	private void initDataView() {
		final DataView<Recipe> dataView = new DataView<Recipe>("simple",
				new RecipeSearchDataProvider(recipeDao, searchMode, keyword)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Recipe> item) {
				initDataViewComponents(item);
			}

		};
		dataView.setItemsPerPage(5L);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
	}

	private void initDataViewComponents(final Item<Recipe> item) {
		final Recipe recipe = item.getModelObject();
		final WebMarkupContainer recipeContainer = new WebMarkupContainer("recipecontainer");
		recipeContainer.add(getTitle(recipe.getTitle()));
		recipeContainer.add(getProducts(recipe));

		recipeContainer.add(new AjaxEventBehavior("click") {

			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				final PageParameters params = new PageParameters();
				params.add(CommonConst.PARAM_RECIPE, recipe.getId());
				setResponsePage(ViewRecipePage.class, params);
			}

		});
		item.add(recipeContainer);
		item.add(new ImagePanel("image", Model.of(recipe.getPhoto()), this));
	}

	private RepeatingView getTitle(final String title) {
		final RepeatingView view = new RepeatingView(Recipe.TITLE_CONST);
		constructLabel(view, title, true);
		return view;
	}

	private RepeatingView getProducts(final Recipe recipe) {
		final ImmutableList<String> products = FluentIterable.from(recipe.getIngredients())
				.transform((final Ingredient ing) -> ing.getFoodProduct().getName()).toList();
		final RepeatingView view = new RepeatingView("products");
		final Iterator<String> iter = products.iterator();
		while (iter.hasNext()) {
			final String productName = iter.next();
			constructLabel(view, productName, false);
			if (iter.hasNext()) {
				view.add(new Label(view.newChildId(), ", ")
						.add(new AttributeAppender(CommonConst.CLASS, "productseparation")));
			}
		}
		return view;
	}

	private void constructLabel(final RepeatingView view, final String productName, final boolean addSpaces) {
		final String productNameInsensitive = productName.toLowerCase();
		if (keyword.isPresent() && productNameInsensitive.contains(keyword.get().toLowerCase())) {
			final String keywordInsensitive = keyword.get().toLowerCase();
			int i = 0;
			final String[] split = productNameInsensitive
					.split(MessageFormat.format("((?<={0})|(?={0}))", keywordInsensitive));
			for (final String string : split) {
				final Label currentLabel = i == 0 ? new Label(view.newChildId(), StringUtils.capitalize(string))
						: new Label(view.newChildId(), string);
				view.add(currentLabel);
				handleKeywordAspect(addSpaces, keywordInsensitive, i, string, currentLabel);
				view.add(currentLabel);
				i++;
			}
		} else {
			view.add(new Label(view.newChildId(), productName));
		}
	}

	private void handleKeywordAspect(final boolean addSpaces, final String keywordInsensitive, final int i,
			final String string, final Label currentLabel) {
		if (string.equals(keywordInsensitive)) {
			currentLabel.add(new AttributeAppender(CommonConst.CLASS, "productkeyword"));
			if (addSpaces) {
				if (i == 0) {
					currentLabel.add(new AttributeAppender(CommonConst.CLASS, " titleright"));
				} else {
					currentLabel.add(new AttributeAppender(CommonConst.CLASS, " titleleft titleright"));
				}
			}
		}
	}
}
