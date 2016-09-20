package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageMain;
import mobile.page.base.Entrance;
import mobile.page.base.EntranceClass;
import mobile.page.entries.EntrancesRR;

/**
 * 融资融券主界面
 * @category Reused
 * @since 2016-08-01
 */
@EntranceClass(EntrancesRR.class)
public class PageMainRR extends AbstractPageMain {
	@Entrance("DBPMR")
	private WebElement oBtnDBPMR;	//担保品买入
	@Entrance("DBPMC")
	private WebElement oBtnDBPMC;	//担保品卖出
}
