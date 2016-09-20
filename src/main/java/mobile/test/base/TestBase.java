package mobile.test.base;

import mobile.page.base.AbstractPage;
import mobile.page.base.PageManager;

class TestBase implements IRecovery {

	@Override
	public void recovery() {
		AbstractPage p = PageManager.getPage(PageManager.getLastUsed());
		
		if(p != null) {
			p.reset();
		}
	}

}
