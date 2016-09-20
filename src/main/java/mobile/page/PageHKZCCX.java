package mobile.page;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.WaitUtil;

/**
 * 港股通资产查询界面
 * @category Stage-02
 * @since 2016-08-15
 */
@SetMain(PageMainHK.class)
public class PageHKZCCX extends AbstractPage {
	private WebElement oTextKQ;
	private WebElement oTextKY;
	private WebElement oImgLoad;
	
	public Map<String, String> doGetVaules() {
		WaitUtil.untilGone(driver, oImgLoad, WaitUtil.WAIT_MEDIUM);
		Map<String, String> map = new HashMap<>();
		map.put("可取", "可取:" + getText(oTextKQ));
		map.put("可用", "可用:" + getText(oTextKY));
		return map;
	}

	@Override
	public void reset() {
		// do nothing
	}

}
