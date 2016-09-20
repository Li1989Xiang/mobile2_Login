package mobile.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.WaitUtil;

/**
 * 资金调度界面
 * 
 * @category Stage-02
 * @since 2016-08-15
 */
@SetMain(PageMainRoot.class)
public class PageZJDD extends AbstractPage {
	private WebElement oBtnCollect;
	private List<WebElement> oTextMoneys;
	private WebElement oImgLoad;
	// 主辅划转界面
	private WebElement oBtnSwitch;
	private WebElement oEditNum;
	private WebElement oEditPwd;
	private WebElement oBtnOK;

	/**
	 * 获取辅账户中所有资金总额
	 * @return
	 */
	public float doGetTotalCanTransfer() {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		
		float total = 0;
		String value = null;

		for (WebElement e : oTextMoneys) {
			//去除多余的汉字、逗号和回车
			value = getText(e).replaceAll("[,\n\r\u4e00-\u9fa5]", "");
			total += Float.valueOf(value);
		}

		return total;
	}

	/**
	 * 点击一键归集按钮
	 */
	public void doCollect() {
		oBtnCollect.click();
	}

	/**
	 * 点击调度按钮，输入划转金额
	 * @param zzh 主账户
	 * @param fzh 辅账户
	 * @param type 类型：主转辅、辅转主
	 * @param num 划转数量
	 */
	public void doOpenTransfer(String zzh, String fzh, String type, String num) {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		
		boolean needSwitch = false;

		if ("主转辅".equals(type)) {
			needSwitch = true;
		}

		String xpath = "//dd[contains(text(), '" + fzh + "')]/following-sibling::dd[1]/em";
		driver.getRealDriver().findElement(By.xpath(xpath)).click();
		WaitUtil.sleep(500);

		if (needSwitch) {
			oBtnSwitch.click();
		}

		getKeyboard().doInput(oEditNum, num);
		oBtnOK.click();
	}

	public void doInputPwd(String pwd) {
		getKeyboard().doInput(oEditPwd, pwd);
	}

	@Override
	public void reset() {
		if(WaitUtil.exists(driver, oBtnSwitch, WaitUtil.WAIT_SHORT)) {
			getToolBar().doBack();
		} else {
			super.reset();
		}
	}

}
