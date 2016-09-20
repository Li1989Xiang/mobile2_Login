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
import up.light.utils.WaitUtil;

/**
 * 一键归集测试
 */
public class TestD03YJGJ extends TestPT {
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
	public void testYJGJ(Map<String, String> param) {
		//获取所有辅账户总金额
		float expectTotal = mPage.doGetTotalCanTransfer();
		//点击一键归集
		mPage.doCollect();
		//校验对话框中的金额
		Alert alert = mPage.getAlert();
		String text = alert.doGetText();
		text = text.substring(text.indexOf("归集 ") + 3, text.indexOf(" 元到主账户"));
		text = text.replace(",", "");
		float actualTotal = Float.valueOf(text);
		AssertUtil.assertEquals(expectTotal, actualTotal);
		alert.doAccept();
		//校验结果
		AssertUtil.assertContains(alert.doGetText(), param.get("验证1"));
		alert.doAccept();
		WaitUtil.sleep(1000);
	}
}
