package com.neo064.recipe.client.pages.recipe.add.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.neo064.recipe.client.common.utils.CommonUtils;
import com.neo064.recipe.client.pages.recipe.add.AddRecipePageConst;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.model.Step;

/**
 * Display a list of steps with the possibility to add or remove one.
 *
 * @author Neo
 *
 */
public class StepWebElement extends ListSortableWebElement<Step> {

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
	public StepWebElement(final String mainPanelId, final String listWebElementId, final String listId,
			final Form<?> form) {
		super(mainPanelId, listWebElementId, listId, form);
	}

	@Override
	protected void populateList(final WebMarkupContainer markupContainer, final ListItem<Step> item) {
		super.populateList(markupContainer, item);
		final TextArea<String> descriptionField = CommonUtils
				.addChangeEvent((TextArea<String>) new TextArea<String>(Step.DESCRIPTION_CONST).setRequired(true)
						.setLabel(Model.<String>of("Description")));
		markupContainer.add(descriptionField);
		markupContainer.add(new FeedbackPanel("feedbackStep", new ComponentFeedbackMessageFilter(descriptionField)));
	}

	@Override
	protected void onAddButtonClick(final AjaxRequestTarget target, final Form<?> form) {
		getModelObject().add(new Step((Recipe) form.getModelObject()));
		recalculateItemNumbers();
		target.add(asComponent());
	}

	@Override
	protected String getAddBtnId() {
		return AddRecipePageConst.ADD_PAGE_ADD_STEP;
	}

	@Override
	protected String getRemoveBtnId() {
		return AddRecipePageConst.ADD_PAGE_REMOVE_STEP;
	}

	@Override
	protected String getRowContainerId() {
		return AddRecipePageConst.ADD_PAGE_STEP_CONTAINER;
	}
}
