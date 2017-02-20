package com.neo064.recipe.client.pages.recipe.view;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.neo064.recipe.client.common.ui.FormMode;
import com.neo064.recipe.client.common.ui.field.image.ImagePanel;
import com.neo064.recipe.client.common.ui.time.TimeField;
import com.neo064.recipe.client.common.utils.CommonConst;
import com.neo064.recipe.client.common.utils.Path;
import com.neo064.recipe.client.pages.common.BasePage;
import com.neo064.recipe.client.pages.recipe.add.AddRecipePage;
import com.neo064.recipe.client.pages.recipe.add.AddRecipePageConst;
import com.neo064.recipe.model.FoodProduct;
import com.neo064.recipe.model.Ingredient;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.model.Step;
import com.neo064.recipe.model.Unit;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;

/**
 * Class displaying the page used to view a Recipe (view mode : no
 * modification).
 *
 * @author Neo
 *
 */
public class ViewRecipePage extends BasePage {

	@SpringBean
	private IRecipeDao dao;

	private final Recipe recipe;

	private final CompoundPropertyModel<Recipe> model;

	private Form<Recipe> form;

	private static final FormMode formMode = FormMode.VIEW;

	/**
	 * Basic constructor.
	 */
	public ViewRecipePage() {
		super();
		recipe = dao.findById(1L);
		model = new CompoundPropertyModel<>(recipe);
		setDefaultModel(model);
		initForm();
	}

	/**
	 * Constructor called when passing http parameters.
	 *
	 * @param params
	 *            the {@link PageParameters} passed through a POST.
	 */
	public ViewRecipePage(final PageParameters params) {
		super(params);
		recipe = dao.findById(params.get(CommonConst.PARAM_RECIPE).toLongObject());
		model = new CompoundPropertyModel<>(recipe);
		setDefaultModel(model);
		initForm();
	}

	private void initForm() {
		form = new Form<Recipe>(AddRecipePageConst.MAIN_FORM, model) {

			@Override
			protected void onSubmit() {
				final PageParameters params = new PageParameters();
				params.add("recipe", recipe.getId());
				setResponsePage(AddRecipePage.class, params);
			}
		};
		initSimpleFields(form);
		initRows(form);
		add(form);
	}

	private void initSimpleFields(final Form<Recipe> form) {
		form.add(new Label(Recipe.TITLE_CONST));
		form.add(new Button(CommonConst.MODIFY_BUTTON));
		form.add(new ImagePanel("image", model.bind("photo"), this));
		form.add(new TimeField(Recipe.PREPARATION_TIME, "Temps de préparation", formMode).setEnabled(false));
		form.add(new TimeField(Recipe.COOKING_TIME, "Temps de cuisson", formMode).setEnabled(false));

		final WebMarkupContainer container = new WebMarkupContainer(Recipe.VIDEO_URL);
		container.add(new AttributeAppender("src", Model.of(recipe.getVideoURL())));
		form.add(container);
	}

	private void initRows(final Form<Recipe> form) {
		form.add(getIngredientsListView());
		form.add(getStepsListView());
	}

	private ListView<Ingredient> getIngredientsListView() {
		return new ListView<Ingredient>(Recipe.INGREDIENTS_CONST) {

			@Override
			protected void populateItem(final ListItem<Ingredient> item) {
				final CompoundPropertyModel<Ingredient> itemModel = new CompoundPropertyModel<>(item.getModel());
				item.setModel(itemModel);
				item.add(new Label(Ingredient.QUANTITY_CONST));
				item.add(new Label(Ingredient.UNIT_CONST, new CompoundPropertyModel<>(
						itemModel.bind(Path.concat(Ingredient.UNIT_CONST, Unit.ABBREVIATION_CONST)))));
				item.add(new Label(Ingredient.FOOD_PRODUCT_CONST, new CompoundPropertyModel<>(
						itemModel.bind(Path.concat(Ingredient.FOOD_PRODUCT_CONST, FoodProduct.NAME_CONST)))));

			}
		};
	}

	private ListView<Step> getStepsListView() {
		return new ListView<Step>(Recipe.STEPS_CONST) {

			@Override
			protected void populateItem(final ListItem<Step> item) {
				final CompoundPropertyModel<Step> itemModel = new CompoundPropertyModel<>(item.getModel());
				item.setModel(itemModel);
				item.add(new Label(Step.DESCRIPTION_CONST));
			}
		};
	}
}
