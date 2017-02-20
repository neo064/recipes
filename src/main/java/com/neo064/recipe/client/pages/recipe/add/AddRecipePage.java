package com.neo064.recipe.client.pages.recipe.add;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.google.common.base.Optional;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.neo064.recipe.client.common.ui.FormMode;
import com.neo064.recipe.client.common.ui.field.image.ImagePanel;
import com.neo064.recipe.client.common.ui.field.upload.UploadFieldPanel;
import com.neo064.recipe.client.common.ui.time.TimeField;
import com.neo064.recipe.client.common.utils.CommonConst;
import com.neo064.recipe.client.pages.common.BasePage;
import com.neo064.recipe.client.pages.recipe.add.ui.IngredientWebElement;
import com.neo064.recipe.client.pages.recipe.add.ui.StepWebElement;
import com.neo064.recipe.client.pages.recipe.view.ViewRecipePage;
import com.neo064.recipe.model.Recipe;
import com.neo064.recipe.server.data.dao.recipe.IRecipeDao;

/**
 * Class displaying the page used to add/modify a Recipe.
 *
 * @author Neo
 *
 */
public class AddRecipePage extends BasePage {

	@SpringBean
	private IRecipeDao dao;

	private final Recipe recipe;

	private final CompoundPropertyModel<Recipe> model;

	private Form<Recipe> form;

	private ImagePanel imagePanel;

	private static final FormMode formMode = FormMode.EDIT;

	/**
	 * Default constructor.
	 */
	public AddRecipePage() {
		super();
		recipe = new Recipe();
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
	public AddRecipePage(final PageParameters params) {
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
				final Recipe savedRecipe = dao.save(getModelObject());
				final PageParameters params = new PageParameters();
				params.add(CommonConst.PARAM_RECIPE, savedRecipe.getId());
				setResponsePage(ViewRecipePage.class, params);
			}
		};
		initSimpleFields(form);
		initUploadField(form);
		initRows(form);
		add(form);
	}

	private void initSimpleFields(final Form<Recipe> form) {
		final Component titleField = new TextField<String>(Recipe.TITLE_CONST).setRequired(true)
				.setOutputMarkupId(true);
		form.add(titleField);
		final Button button1 = new Button("savebtn");
		form.add(button1);
		form.add(new FeedbackPanel("titleFeedback", new ComponentFeedbackMessageFilter(titleField)));
		// create the file upload field
		imagePanel = new ImagePanel("image", model.bind("photo"), this);
		form.add(new TimeField(Recipe.PREPARATION_TIME, "Temps de préparation", formMode));
		form.add(new TimeField(Recipe.COOKING_TIME, "Temps de cuisson", formMode));
		form.add(new TextField<>(Recipe.VIDEO_URL));
		form.add(imagePanel);
	}

	private void initUploadField(final Form<Recipe> form) {
		final UploadFieldPanel uploadFieldPanel = new UploadFieldPanel("photoUpload", form, Optional.of("image/*")) {

			@Override
			public void onFieldSelected(final Form<?> form, final FileUploadField uploadFile,
					final AjaxRequestTarget target) {
				AddRecipePage.this.onFieldSelected(form, uploadFile, target);
			}
		};
		form.add(uploadFieldPanel);
	}

	private void onFieldSelected(final Form<?> form, final FileUploadField uploadFile, final AjaxRequestTarget target) {
		final FileUpload fileUpload = uploadFile.getFileUpload();
		if (fileUpload != null) {
			recipe.setPhoto(fileUpload.getBytes());
			imagePanel.refresh();
		}
		target.add(form);
	}

	private void initRows(final Form<Recipe> form) {
		final StepWebElement stepWebElement = new StepWebElement(AddRecipePageConst.STEP_ROW_PANEL,
				AddRecipePageConst.STEP_LIST, Recipe.STEPS_CONST, form);
		stepWebElement.setDefaultModel(new CompoundPropertyModel<>(recipe.getSteps()));

		final IngredientWebElement ingredientWebElement = new IngredientWebElement(
				AddRecipePageConst.INGREDIENT_ROW_PANEL, AddRecipePageConst.INGREDIENT_LIST, Recipe.INGREDIENTS_CONST,
				form);
		ingredientWebElement.setDefaultModel(new CompoundPropertyModel<>(recipe.getIngredients()));

		form.add(stepWebElement.asComponent());
		form.add(ingredientWebElement.asComponent());
	}
}
