package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageMain;
import mobile.page.base.Entrance;
import mobile.page.base.EntranceClass;
import mobile.page.entries.EntrancesRoot;

/**
 * 普通交易主界面
 * @category Reused
 * @since 2016-08-01
 */
@EntranceClass(EntrancesRoot.class)
public class PageMainRoot extends AbstractPageMain {
	@Entrance("GPMR")
	private WebElement oBtnGPMR;	//股票买入
	@Entrance("GPMC")
	private WebElement oBtnGPMC;	//股票卖出
	@Entrance("ZCCX")
	private WebElement oBtnZCCX;	//资产查询
	@Entrance("YZZZ")
	private WebElement oBtnYZZZ;	//银证转账
	@Entrance("JJ")
	private WebElement oBtnJJ;		//基金
	@Entrance("ZJDD")
	private WebElement oBtnZJDD;	//资金调度
	@Entrance("GGT")
	private WebElement oBtnGGT;		//港股通
	@Entrance("LOGOUT")
	private WebElement oBtnLogOut;		//港股通
}
