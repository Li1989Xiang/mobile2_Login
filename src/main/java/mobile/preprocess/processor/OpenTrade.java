package mobile.preprocess.processor;

import mobile.page.base.PageManager;
import mobile.page.module.ToolBar;
import mobile.preprocess.Preprocessor;
import mobile.preprocess.RunBeforeAll;

@RunBeforeAll
public class OpenTrade extends Preprocessor {
	private ToolBar mToolBar = PageManager.getPage(ToolBar.class);

	@Override
	public Object[] getParameter() {
		return null;
	}

	@Override
	public void process(Object[] param) {
		mToolBar.doOpenTrade();
	}

	@Override
	public void recovery() {
	}
}
