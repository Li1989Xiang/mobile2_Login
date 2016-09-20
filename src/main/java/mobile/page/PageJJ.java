package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.Conditions;
import up.light.utils.WaitUtil;

/**
 * 基金认申赎
 * @category Stage-01
 * @since 2016-08-01
 */
@SetMain(PageMainJJ.class)
public class PageJJ extends AbstractPage {
	private WebElement oEditCode;	//基金代码
	private WebElement oTextName;	//基金名称
	private WebElement oEditNum;	//申购、认购、赎回数量
	private WebElement oBtnTrade;	//交易按钮
	private WebElement oTextPrice;	//基金净值
	private WebElement oTextMoney;	//可用资金
	private WebElement oTextNumber;	//可赎份额
	private WebElement oMsgRisk;	//风险提示对话框文本
	
	/**
	 * 输入基金代码
	 * @param code 基金代码
	 */
	public String doInputCode(String code) {
		//等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2);
		//输入代码并等待数据加载
		getKeyboard().doInput(oEditCode, code);
		WaitUtil.waitForText(driver, oTextName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		
		return getText(oTextName);
	}
	
	/**
	 * 输入数量
	 * @param number 
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditNum, number);
	}
	
	/**
	 * 点击认购，申购或赎回按钮
	 */
	public void doTrade() {
		oBtnTrade.click();
	}
	
	/**
	 * 获取价格编辑框中的文本
	 * @return 股票价格文本
	 */
	public String doGetPrice() {
		return getText(oTextPrice);
	}
	
	/**
	 * 获取可用资金文本
	 * @return 可用资金文本
	 */
	public String doGetMoney() {
		return getText(oTextMoney);
	}
	
	/**
	 * 获取可赎份额
	 * @return 可赎份额文本
	 */
	public String doGetNumber() {
		return getText(oTextNumber);
	}
	
	/**
	 * 处理风险提示对话框
	 */
	public void doHandleRiskAlert() {
		if(WaitUtil.exists(driver, oMsgRisk, 1)) {
			getAlert().doAccept();
		}
	}
}
