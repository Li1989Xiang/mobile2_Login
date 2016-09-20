package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageHKBDZQ;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesHK;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 港股通标的证券测试
 */
public class TestE03HKBDZQ extends TestPT {
	private PageHKBDZQ mPage = PageManager.getPage(PageHKBDZQ.class);
	private AbstractPageMain mPageMain = mPage.getMain();
	private AbstractPageMain mPageMainRoot = mPageMain.getMain();

	@BeforeClass
	public void before1() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMainRoot.doEnter(EntrancesRoot.GGT);
	}
	
	@BeforeClass
	public void before2() {
		mPageMain.doEnter(EntrancesHK.BDZQ);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKBDZQ(Map<String, String> param) {
		mPage.doInitializeMap();
		AssertUtil.assertEquals(param.get("名称"), mPage.doGetName(param.get("代码")));
	}
}
