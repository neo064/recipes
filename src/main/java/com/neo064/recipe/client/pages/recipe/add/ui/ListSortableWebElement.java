package com.neo064.recipe.client.pages.recipe.add.ui;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.neo064.recipe.model.ISortable;

/**
 * Class handling a list of sortable elements that can be added or removed.
 *
 * @author Neo
 *
 * @param <T>
 */
public abstract class ListSortableWebElement<T extends ISortable> extends Sortable<T> {

	private final String listId;

	private final MarkupContainer componentContainer;

	private final Form<?> form;

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
	public ListSortableWebElement(final String mainPanelId, final String listWebElementId, final String listId,
			final Form<?> form) {
		super(listWebElementId, Lists.newArrayList());
		this.listId = listId;
		this.componentContainer = new WebMarkupContainer(mainPanelId);
		this.form = form;
		componentContainer.setOutputMarkupId(true);
		componentContainer.add(this);
		initAddButton();
	}

	private void initAddButton() {
		final AjaxSubmitLink addLink = new AjaxSubmitLink(getAddBtnId(), form) {
			@Override
			public void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				onAddButtonClick(target, form);
				recalculateItemNumbers();
			}
		};
		addLink.setDefaultFormProcessing(false);
		add(addLink);

	}

	@Override
	protected HashListView<T> newListView(final IModel<List<T>> model) {
		return (HashListView<T>) new HashListView<T>(listId, model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<T> item) {
				final CompoundPropertyModel<T> model = new CompoundPropertyModel<>(item.getModel());
				item.setModel(model);
				final WebMarkupContainer container = new WebMarkupContainer(getRowContainerId());
				container.setOutputMarkupId(true);
				item.add(container);
				populateList(container, item);
			}
		}.setReuseItems(true);
	}

	protected void populateList(final WebMarkupContainer markupContainer, final ListItem<T> item) {

		markupContainer.add(new AjaxSubmitLink(getRemoveBtnId()) {
			@Override
			public void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				ListSortableWebElement.this.getModelObject().remove(item.getIndex());
				recalculateItemNumbers();
				if (target != null) {
					target.add(asComponent());
				}
			}
		}.setDefaultFormProcessing(false));
	}

	@Override
	public void onUpdate(final AjaxRequestTarget target, final T item, final int index) {
		final List<T> modelObject = getModelObject();
		final T replaced = modelObject.get(index);
		replaced.setNumber(item.getNumber());
		item.setNumber((byte) index);
	}

	protected void recalculateItemNumbers() {
		byte i = 0;
		for (final T item : this.getModelObject()) {
			item.setNumber(i);
			i++;
		}
	}

	@Override
	protected T findItem(final String id, final List<T> list) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		return super.findItem(id, list);
	}

	/**
	 *
	 * @return this object as a {@link MarkupContainer} component.
	 */
	public MarkupContainer asComponent() {
		return componentContainer;
	}

	protected abstract String getRowContainerId();

	protected abstract void onAddButtonClick(AjaxRequestTarget target, Form<?> form);

	protected abstract String getAddBtnId();

	protected abstract String getRemoveBtnId();
}
