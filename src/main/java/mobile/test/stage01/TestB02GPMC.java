package mobile.test.stage01;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.Page2GP;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.Alert;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;
import mobile.test.utils.DBCheck;

/**
 * 股票卖出测试
 */
public class TestB02GPMC extends TestPT {
	private Page2GP mPage = PageManager.getPage(Page2GP.class);
	private AbstractPageMain mPageMain = mPage.getMain();
	
	@BeforeClass
	public void before() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMain.doEnter(EntrancesRoot.GPMC);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJYMC(Map<String, String> param) {
		//输入代码并校验股票名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);
		
		//输入数量
		mPage.doInputNumber(param.get("数量"));
		
		//读取实际价格并替换
		String vActualPrice = mPage.doGetPrice();
		String vCheckPoint1 = param.get("验证1");
		vCheckPoint1 = vCheckPoint1.replace("{PRICE}", vActualPrice);
		
		//点击买入
		mPage.doTrade();
		
		//获取对话框1内容并校验
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();
		
		//获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();
		
		//参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
		
		//校验数据库
		DBCheck.getInstance().checkOrder(vNo, vExpectName, param.get("代码"), vActualPrice, param.get("数量"));
	}
}
