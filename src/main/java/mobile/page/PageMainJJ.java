package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageMain;
import mobile.page.base.Entrance;
import mobile.page.base.EntranceClass;
import mobile.page.base.SetMain;
import mobile.page.entries.EntrancesFund;

/**
 * 基金主界面
 * @category Reused
 * @since 2016-08-01
 */
@SetMain(PageMainRoot.class)	// 二级主界面，需设置PageMain
@EntranceClass(EntrancesFund.class)
public class PageMainJJ extends AbstractPageMain {
	@Entrance("RG")
	private WebElement oBtnRG;	//认购
	@Entrance("SG")
	private WebElement oBtnSG;	//申购
	@Entrance("SH")
	private WebElement oBtnSH;	//赎回
	@Entrance("DTDS")
	private WebElement oBtnDTDS;	//基金定投定赎
}
