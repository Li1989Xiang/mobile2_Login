package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageMain;
import mobile.page.base.Entrance;
import mobile.page.base.EntranceClass;
import mobile.page.entries.EntrancesWo;

@EntranceClass(EntrancesWo.class)
public class PageWo extends AbstractPageMain {
	@Entrance("SYSTEMSETUP")
	private WebElement mSystemSetUp;	//系统设置
}
