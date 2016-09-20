package mobile.test.stage01;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageZCCX;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 资产查询测试
 */
public class TestB00ZCCX extends TestPT {
	PageZCCX mPage = PageManager.getPage(PageZCCX.class);
	AbstractPageMain mPageMain = mPage.getMain();

	@BeforeClass
	public void before() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMain.doEnter(EntrancesRoot.ZCCX);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testZCCX(Map<String, String> param) {
		//切换币种
		mPage.doSwitchTo(param.get("资金类型"));
		//获取资金信息
		Map<String, String> m = mPage.doGetNumbers();
		//获取验证信息
		String expectKY = param.get("验证1");
		String expectKQ = param.get("验证2");
		String ky = "可用", kq = "可取";
		//校验可用、可取
		AssertUtil.assertEquals(expectKY, ky + ":" + m.get(ky));
		AssertUtil.assertEquals(expectKQ, kq + ":" + m.get(kq));
	}
}
