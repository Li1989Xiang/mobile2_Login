package mobile.page.base;

import mobile.page.module.AlertHK;

public class AbstractPageHK extends AbstractPage {

	public AlertHK getAlertHK() {
		return PageManager.getPage(AlertHK.class);
	}
}
