package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sapium.assertutil.AssertUtil;

import mobile.page.PageHKGSXW;
import mobile.page.base.AbstractPageMain;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesHK;
import mobile.page.entries.EntrancesRoot;
import mobile.page.module.AlertHK;
import mobile.page.module.ToolBar;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPT;

/**
 * 港股通公司行为测试
 */
public class TestE04HKGSXW extends TestPT {
	private PageHKGSXW mPage = PageManager.getPage(PageHKGSXW.class);
	private AbstractPageMain mPageMain = mPage.getMain();
	private AbstractPageMain mPageMainRoot = mPageMain.getMain();

	@BeforeClass
	public void before1() {
		mPage.getToolBar().switchTo(ToolBar.PTJY);
		mPageMainRoot.doEnter(EntrancesRoot.GGT);
	}
	
	@BeforeClass
	public void before2() {
		mPageMain.doEnter(EntrancesHK.GSXW);
	}
	
	@AfterClass
	public void after() {
		mPage.backToRootMain();
	}
	
	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testHKBDZQ(Map<String, String> param) {
		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);
		//选择业务类型
		mPage.doChaneType(true, param.get("业务类型"));
		//输入行为代码
		mPage.doInputXWCode(param.get("行为代码"));
		//选择申报类型
		String type = param.get("申报类型");
		mPage.doChaneType(false, type);
		// 输入数量、点击买入
		if(!"查询".equals(type)) {
			mPage.doInputNumber(param.get("数量"));
		}
		mPage.doTrade();
		// 获取对话框1内容并校验
		AlertHK alert = mPage.getAlertHK();
		String vCheckPoint1 = param.get("验证1");
		String vActualCheckPoint1 = alert.doGetConfirmText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAcceptConfirm();
		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetResultText();
		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAcceptResult();
		// 参数中加入委托编号
		String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
		param.put("委托编号", vNo);
	}
}
