package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageZJDD;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.Alert;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 主辅划转测试
 */
public class TestD02ZFHZ extends TestPT {
	private PageZJDD mPage = PageManager.getPage(PageZJDD.class);
	private AbstractPageMain mPageMain = mPage.getMain();

	@BeforeClass
	public void before() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMain.doEnter(EntrancesRoot.ZJDD);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testZFHZ(Map<String, String> param) {
		mPage.doOpenTransfer(param.get("主账户"), param.get("辅账户"), param.get("类型"), param.get("金额"));
		
		Alert alert = mPage.getAlert();
		String actual = alert.doGetText().replace(",", "");
		AssertUtil.assertEquals(param.get("验证1"), actual);
		mPage.doInputPwd(param.get("资金密码"));
		alert.doAccept();
		AssertUtil.assertEquals(param.get("验证2"), alert.doGetText());
		alert.doAccept();
	}
}
