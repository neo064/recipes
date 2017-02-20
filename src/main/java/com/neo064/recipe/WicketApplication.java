package com.neo064.recipe;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.neo064.recipe.client.pages.common.search.SearchPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.neo064.recipe.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return SearchPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		onInitialize();
	}

	protected void onInitialize() {
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		this.getMarkupSettings().setStripWicketTags(true);
		getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
	}
}
