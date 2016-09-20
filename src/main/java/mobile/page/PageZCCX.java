package mobile.page;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.ArgumentUtil;
import up.light.utils.WaitUtil;

/**
 * 资产查询
 * @category Stage-01
 * @since 2016-08-01
 */
@SetMain(PageMainRoot.class)
public class PageZCCX extends AbstractPage {
	private WebElement oTitleRMB;	//人民币
	private WebElement oTitleHKD;	//港币
	private WebElement oTitleUSD;	//美元
	private WebElement oTextKQ;		//可用
	private WebElement oTextKY;		//可取
	private WebElement oImgLoad;	//加载浮层
	
	/**
	 * 切换币种
	 * @param name 币种
	 */
	public void doSwitchTo(String name) {
		WaitUtil.sleep(1000);
		WebElement e = null;

		if ("人民币".equals(name)) {
			e = oTitleRMB;
		} else if ("港币".equals(name)) {
			e = oTitleHKD;
		} else if ("美元".equals(name)) {
			e = oTitleUSD;
		}

		ArgumentUtil.notNull(e, "unsupport currency: " + name);
		e.click();
		WaitUtil.sleep(1000);
	}

	/**
	 * 获取资产信息
	 * @return 返回包含资产数值的Map
	 */
	public Map<String, String> doGetNumbers() {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_LONG);
		Map<String, String> m = new HashMap<>();
		m.put("可用", oTextKY.getText());
		m.put("可取", oTextKQ.getText());
		return m;
	}

	@Override
	public void reset() {
		// do nothing
	}
}
