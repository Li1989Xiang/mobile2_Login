package mobile.preprocess.processor;

import mobile.page.PageLogin;
import mobile.page.base.PageManager;
import mobile.preprocess.Preprocessor;

/**
 * 融资融券登录预处理器
 */
public class LoginRR extends Preprocessor {
	private PageLogin mPageLogin = PageManager.getPage(PageLogin.class);

	@Override
	public Object[] getParameter() {
		return new String[]{"99000002", "123321"};
	}

	@Override
	public void process(Object[] param) {
		mPageLogin.dobeforeLogin((String)param[0], (String)param[1]);
		mPageLogin.doLogin();
	}

	@Override
	public void recovery() {
		mPageLogin.getToolBar().doBack();
	}
}
