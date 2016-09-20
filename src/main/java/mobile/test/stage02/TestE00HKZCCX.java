package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageHKZCCX;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesHK;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 港股通资产查询测试
 */
public class TestE00HKZCCX extends TestPT {
	private PageHKZCCX mPage = PageManager.getPage(PageHKZCCX.class);
	private AbstractPageMain mPageMain = mPage.getMain();
	private AbstractPageMain mPageMainRoot = mPageMain.getMain();

	@BeforeClass
	public void before1() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMainRoot.doEnter(EntrancesRoot.GGT);
	}
	
	@BeforeClass
	public void before2() {
		mPageMain.doEnter(EntrancesHK.ZCCX);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKZCCX(Map<String, String> param) {
		Map<String, String> map = mPage.doGetVaules();
		String actualKQ = map.get("可取");
		String actualKY = map.get("可用");
		AssertUtil.assertEquals(param.get("验证1"), actualKQ);
		AssertUtil.assertEquals(param.get("验证2"), actualKY);
	}
}
