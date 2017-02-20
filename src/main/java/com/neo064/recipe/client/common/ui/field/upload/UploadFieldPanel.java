package com.neo064.recipe.client.common.ui.field.upload;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public abstract class UploadFieldPanel extends Panel {

	public UploadFieldPanel(final String id, final Form<?> form, final Optional<String> acceptType) {
		super(id);
		final List<FileUpload> files = Lists.newArrayList();
		final FileUploadField uploadFile = new FileUploadField("upload", Model.ofList(files));

		uploadFile.add(new AjaxFormSubmitBehavior(form, "change") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				onFieldSelected(form, uploadFile, target);
			}

			@Override
			public boolean getDefaultProcessing() {
				return false;
			}
		});
		uploadFile.setOutputMarkupId(true);
		if (acceptType.isPresent()) {
			uploadFile.add(new AttributeModifier("accept", acceptType.get()));
		}

		add(uploadFile);
	}

	public abstract void onFieldSelected(Form<?> form, FileUploadField uploadFile, AjaxRequestTarget target);

}
