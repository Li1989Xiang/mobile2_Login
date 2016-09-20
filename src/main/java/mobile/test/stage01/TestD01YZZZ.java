package mobile.test.stage01;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageYZZZ;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.Alert;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 银证转账测试
 */
public class TestD01YZZZ extends TestPT {
	private PageYZZZ mPage = PageManager.getPage(PageYZZZ.class);
	private AbstractPageMain mPageMain = mPage.getMain();
	
	@BeforeClass
	public void before() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMain.doEnter(EntrancesRoot.YZZZ);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testYZZZ(Map<String, String> param) {
		String type = param.get("转账方式");

		mPage.doSwithTo(type);
		mPage.doSetBank(param.get("银行名称"), param.get("币种"));
		mPage.doInputNum(param.get("转账金额"));

		if ("银行转证券".equals(type)) {
			mPage.doInputPwd(param.get("银行密码"));
			mPage.doTrade();
		} else {
			mPage.doTrade();
			mPage.doInputPwd(param.get("资金密码"));
		}
		
		Alert alert = mPage.getAlert();
		String actual1 = alert.doGetText();
		AssertUtil.assertEquals(param.get("验证1"), actual1);
		alert.doAccept();

		String actual2 = alert.doGetText();
		AssertUtil.assertEquals(param.get("验证2"), actual2);
		alert.doAccept();
	}
}
