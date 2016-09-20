package mobile.page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;

/**
 * 标的证券界面
 * @category Stage-02
 * @since 2016-08-15
 */
@SetMain(PageMainHK.class)
public class PageHKBDZQ extends AbstractPage {
	@CacheLookup
	private List<WebElement> oTdFirstColumn;// 第一列td
	private Map<String, String> mItems;
	private boolean inited;

	public void doInitializeMap() {
		if (inited)
			return;

		mItems = new LinkedHashMap<>();
		String name = null, code = null;
		
		for (WebElement e : oTdFirstColumn) {
			name = getText(e.findElement(By.xpath("./p[1]")));
			code = getText(e.findElement(By.xpath("./p[2]")));
			System.out.println(name + ": " + code);
			mItems.put(code, name);
		}

		inited = true;
	}

	public String doGetName(String code) {
		String name = mItems.get(code);
		return name == null ? "" : name;
	}

	@Override
	public void reset() {
		// do nothing
	}

}
