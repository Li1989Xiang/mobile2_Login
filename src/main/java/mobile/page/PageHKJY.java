package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageHK;
import mobile.page.base.SetMain;
import up.light.utils.Conditions;
import up.light.utils.WaitUtil;

/**
 * 港股通买入、卖出界面
 * @category Stage-02
 * @since 2016-08-15
 */
@SetMain(PageMainHK.class)
public class PageHKJY extends AbstractPageHK {
	private WebElement oEditCode;
	private WebElement oTextName;
	private WebElement oEditPrice;
	private WebElement oEditNum;
	private WebElement oBtnTrade;
	//股票选择界面
	private WebElement oEditCode2;
	private WebElement oBtnCancel;
	private WebElement oTextFirstItem;
	
	/**
	 * 输入股票代码
	 * @param code 股票代码
	 * @return 股票名称编辑框中的文本
	 */
	public String doInputCode(String code) {
		// 等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2);
		oEditCode.click();
		WaitUtil.sleep(500);
		// 输入代码并选择第一条
		getKeyboard().doInput(oEditCode2, code);
		WaitUtil.sleep(500);
		oTextFirstItem.click();
		// 等待加载完成
		WaitUtil.waitForText(driver, oTextName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);

		return getText(oTextName);
	}
	
	/**
	 * 输入数量
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditNum, number);
	}

	/**
	 * 点击买入或卖出按钮
	 */
	public void doTrade() {
		oBtnTrade.click();
	}

	/**
	 * 获取价格编辑框中的文本
	 * @return 股票价格文本
	 */
	public String doGetPrice() {
		return getValue(oEditPrice);
	}

	/*
	 * 因需要处理位于股票选择界面的异常，所以覆写父类reset方法
	 */
	@Override
	public void reset() {
		// 位于股票选择界面
		if (WaitUtil.exists(driver, oBtnCancel, 1)) {
			oBtnCancel.click();
		}
		// 位于股票买卖界面
		super.reset();
	}
}
