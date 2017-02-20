package com.neo064.recipe.client.pages.recipe.add.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.common.base.Optional;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.neo064.recipe.client.common.ui.field.AutoCompleteLoadableField;
import com.neo064.recipe.client.common.ui.field.LoadableComboBoxField;
import com.neo064.recipe.client.common.utils.CommonConst;
import com.neo064.recipe.client.common.utils.CommonUtils;
import com.neo064.recipe.client.pages.recipe.add.AddRecipePageConst;
import com.neo064.recipe.model.FoodProduct;
import com.neo064.recipe.model.Ingredient;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.model.Unit;
import com.neo064.recipe.server.data.dao.foodproduct.IFoodProductDao;
import com.neo064.recipe.server.data.dao.unit.IUnitDao;

/**
 * Display a list a ingredients with the possibility to add or remove one.
 *
 * @author Neo
 *
 */
public class IngredientWebElement extends ListSortableWebElement<Ingredient> {

	@SpringBean
	private IFoodProductDao dao;

	@SpringBean
	private IUnitDao unitDao;

	/**
	 * Constructor.
	 *
	 * @param mainPanelId
	 *            the panel markup id containing this object
	 * @param listWebElementId
	 *            the id of this {@link ListSortableWebElement} (ul tag)
	 * @param listId
	 *            the id of the model list
	 * @param form
	 *            the page {@link Form}
	 *
	 */
	public IngredientWebElement(final String mainPanelId, final String listWebElementId, final String listId,
			final Form<?> form) {
		super(mainPanelId, listWebElementId, listId, form);
	}

	@Override
	protected void populateList(final WebMarkupContainer markupContainer, final ListItem<Ingredient> item) {
		super.populateList(markupContainer, item);
		final NumberTextField<Float> quantityField = CommonUtils
				.addChangeEvent(new NumberTextField<Float>(Ingredient.QUANTITY_CONST));
		quantityField.setOutputMarkupId(true);
		quantityField.setMinimum(0f);
		quantityField.setRequired(true);
		quantityField.setType(Float.class);
		quantityField.setLabel(Model.of("Quantité"));
		markupContainer.add(quantityField);

		final AutoCompleteLoadableField<FoodProduct> combobox = CommonUtils
				.addChangeEvent(new AutoCompleteLoadableField<FoodProduct>("foodProduct",
						((CompoundPropertyModel<Ingredient>) item.getModel()).bind("foodProduct"),
						new TextRenderer<>("name"), dao, Optional.of(FoodProduct.class)));
		combobox.setLabel(Model.of("Produit"));
		combobox.add(new AjaxFormComponentUpdatingBehavior("blur") {

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				if (combobox.getModelObject() == null && combobox.getInput() != null) {
					combobox.clearInput();
					target.add(combobox);
				}
			}
		});
		combobox.setRequired(true);
		final LoadableComboBoxField<Unit> unitCombo = (LoadableComboBoxField<Unit>) new LoadableComboBoxField<Unit>(
				"unit", new ChoiceRenderer<>("abbreviation"), unitDao, Optional.<String>of("Unité")).setRequired(true)
						.setLabel(Model.<String>of("Unité"));
		unitCombo.add(new AttributeAppender(CommonConst.CLASS, "unit"));
		markupContainer.add(unitCombo);
		markupContainer.add(combobox);
		markupContainer
				.add(new FeedbackPanel("feedbackIngredient", new ContainerFeedbackMessageFilter(markupContainer)));
	}

	@Override
	protected void onAddButtonClick(final AjaxRequestTarget target, final Form<?> form) {
		final Ingredient ingredient = new Ingredient();
		ingredient.setRecipe((Recipe) form.getModelObject());
		getModelObject().add(ingredient);
		recalculateItemNumbers();
		target.add(asComponent());
	}

	@Override
	protected String getAddBtnId() {
		return AddRecipePageConst.ADD_PAGE_ADD_INGREDIENT;
	}

	@Override
	protected String getRemoveBtnId() {
		return AddRecipePageConst.ADD_PAGE_REMOVE_INGREDIENT;
	}

	@Override
	protected String getRowContainerId() {
		return AddRecipePageConst.ADD_PAGE_INGREDIENT_CONTAINER;
	}
}
