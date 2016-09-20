package mobile.page.module;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.IModule;
import up.light.utils.WaitUtil;

/**
 * 港股通对话框
 */
public class AlertHK extends AbstractPage implements IModule {
	//确认对话框
	private List<WebElement> oTextConfirms;
	private WebElement oBtnConfirmOK;
	//委托成功对话框
	private List<WebElement> oTextMsgSuccess;
	private WebElement oBtnOK;
	//委托失败对话框
	private WebElement oTextMsgFail;
	
	public String doGetConfirmText() {
		return getMsg(oTextConfirms);
	}
	
	public String doGetResultText() {
		if(WaitUtil.exists(driver, oBtnOK, WaitUtil.WAIT_MEDIUM)) {
			return getMsg(oTextMsgSuccess);
		}
		
		return getText(oTextMsgFail);
	}
	
	public void doAcceptConfirm() {
		oBtnConfirmOK.click();
	}
	
	public void doAcceptResult() {
		oBtnOK.click();
	}
	
	private String getMsg(List<WebElement> es) {
		StringBuilder sb = new StringBuilder();
		String content = null;
		int i = 1;

		for (WebElement e : es) {
			content = getText(e);

			if (StringUtils.isEmpty(content))
				continue;

			if (i++ % 2 == 0) {
				sb.append(content).append("\n");
			} else {
				sb.append(content).append(" ");
			}
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		} else {
			throw new RuntimeException("text is null, confirm alert exists");
		}

		return sb.toString();
	}
}
