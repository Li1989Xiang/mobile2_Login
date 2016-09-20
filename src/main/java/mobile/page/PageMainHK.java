package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageMain;
import mobile.page.base.Entrance;
import mobile.page.base.EntranceClass;
import mobile.page.base.IEntryPoint;
import mobile.page.base.SetMain;
import mobile.page.entries.EntrancesHK;
import up.light.utils.WaitUtil;

/**
 * 港股通交易主界面
 * @category Reused
 * @since 2016-08-15
 */
@SetMain(PageMainRoot.class)	// 二级主界面，需设置PageMain
@EntranceClass(EntrancesHK.class)
public class PageMainHK extends AbstractPageMain {
	private WebElement oImgLoad;
	@Entrance("MR")
	private WebElement oBtnMR;
	@Entrance("MC")
	private WebElement oBtnMC;
	@Entrance("ZCCX")
	private WebElement oBtnZCCX;
	@Entrance("BDZQ")
	private WebElement oBtnBDZQ;
	@Entrance("GSXW")
	private WebElement oBtnGSXW;
	
	@Override
	public void doEnter(IEntryPoint point) {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		super.doEnter(point);
	}
}
