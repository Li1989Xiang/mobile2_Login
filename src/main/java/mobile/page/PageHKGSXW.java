package mobile.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPageHK;
import mobile.page.base.SetMain;
import mobile.page.module.Alert;
import up.light.utils.Conditions;
import up.light.utils.WaitUtil;

@SetMain(PageMainHK.class)
public class PageHKGSXW extends AbstractPageHK {
	//股票选择界面
	private WebElement oEditCode2;
	private WebElement oTextFirstItem;
	private WebElement oBtnCancel;
	//公司行为界面
	private WebElement oEditCode;
	private WebElement oTextName;
	private WebElement oLabelYWLX;
	private WebElement oEditXWDM;
	private WebElement oLabelSBLX;
	private WebElement oEditWTBH;
	private WebElement oEditSBSL;
	private WebElement oBtnSubmit;
	
	/**
	 * 输入股票代码
	 * @param code 股票代码
	 */
	public String doInputCode(String code) {
		//等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG * 2);
		//输入代码并等待数据加载
		oEditCode.click();
		WaitUtil.sleep(500);
		getKeyboard().doInput(oEditCode2, code);
		oTextFirstItem.click();
		
		Alert alert = getAlert();
		if(alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}
		
		WaitUtil.waitForText(driver, oTextName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		
		return getText(oTextName);
	}
	
	/**
	 * 选择业务、申报类型
	 * @param isYW 是否为业务类型
	 * @param type 类型名称
	 */
	public void doChaneType(boolean isYW, String type) {
		if(isYW) {
			oLabelYWLX.click();
		} else {
			oLabelSBLX.click();
		}
		
		WaitUtil.sleep(200);
		String xpath = "//div[@class='kmc-downMenu']/ul/li[text()='" + type + "']";
		driver.getRealDriver().findElement(By.xpath(xpath)).click();
		
		if("撤销".equals(type)) {
			getKeyboard().doInput(oEditWTBH, "0");
		}
	}
	
	/**
	 * 输入行为代码
	 * @param XWCode 行为代码
	 */
	public void doInputXWCode(String XWCode){
		getKeyboard().doInput(oEditXWDM, XWCode);
	}
	
	/**
	 * 输入数量
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oEditSBSL, number);
	}
	
	/**
	 * 点击提交
	 */
	public void doTrade() {
		oBtnSubmit.click();
	}

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
