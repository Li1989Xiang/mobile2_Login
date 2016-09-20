package mobile.page.module;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.IModule;
import up.light.utils.WaitUtil;

public class ToolBar extends AbstractPage implements IModule {
	public static final byte PTJY = 0;
	public static final byte RZRQ = 1;

	// 上方普通交易按钮
	private WebElement oBtnPTJY;
	// 上方融资融券按钮
	private WebElement oBtnRZRQ;
	// 下方交易按钮
	private WebElement oBtnJY;
	//下方“我”按钮
	private WebElement oBtnWo;
	// 返回按钮
	private WebElement oBtnBack;
	// 当前界面 0 - 普通交易， 1 - 融资融券
	private byte current;

	public void switchTo(byte type) {
		if (current == type) {
			return;
		}
		
		switch (type) {
		case 0:
			oBtnPTJY.click();
			break;
		case 1:
			oBtnRZRQ.click();
			break;
		default:
			break;
		}

		current = type;
	}

	public void doOpenTrade() {
		oBtnJY.click();
	}
	
	public void doOpenWo() {
		oBtnWo.click();
		WaitUtil.sleep(1000);
	}

	public void doBack() {
		oBtnBack.click();
		WaitUtil.sleep(1000);
	}
}
