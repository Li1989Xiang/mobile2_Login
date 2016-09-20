package mobile.page.module;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.IModule;
import up.light.utils.WaitUtil;

public class Alert extends AbstractPage implements IModule {
	// 页面对象
	private List<WebElement> mControls;
	private WebElement oBtnOK;
	private WebElement oBtnCancel;

	/**
	 * 返回对话框内容，每个控件间以空格分隔，每两个控件自动添加回车
	 * 
	 * @return
	 */
	public String doGetText() {
		StringBuilder sb = new StringBuilder();
		String content = null;
		int i = 1;

		for (WebElement e : mControls) {
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

	/**
	 * 点击对话框确认
	 */
	public void doAccept() {
		oBtnOK.click();
	}

	/**
	 * 点击对话框取消
	 */
	public void doCancel() {
		JavascriptExecutor jse = (JavascriptExecutor) driver.getRealDriver();
		jse.executeScript("CloseModal();");
	}

	/**
	 * 对话框是否存在
	 * 
	 * @return
	 */
	public boolean exists() {
		boolean ret;

		ret = WaitUtil.exists(driver, oBtnOK, WaitUtil.WAIT_SHORT);

		if (ret)
			return true;

		return WaitUtil.exists(driver, oBtnCancel, WaitUtil.WAIT_SHORT);
	}
}
