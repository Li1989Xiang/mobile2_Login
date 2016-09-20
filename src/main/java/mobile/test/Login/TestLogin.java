package mobile.test.Login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mobile.page.PageLogin;
import mobile.page.PageMainRR;
import mobile.page.PageMainRoot;
import mobile.page.PageSystemSetUp;
import mobile.page.PageWo;
import mobile.page.base.PageManager;
import mobile.page.entries.EntrancesRR;
import mobile.page.entries.EntrancesRoot;
import mobile.page.entries.EntrancesWo;
import mobile.test.base.TestDataProvider;
import mobile.test.base.TestPTLogin;

public class TestLogin extends TestPTLogin{
	private PageLogin mPage = PageManager.getPage(PageLogin.class);
	private PageWo mPageWo = PageManager.getPage(PageWo.class);
	private PageSystemSetUp mPageSystemSetUp = new PageSystemSetUp();
	private PageMainRoot mPageMainRoot = PageManager.getPage(PageMainRoot.class);
	private PageMainRR mPageMainRR = PageManager.getPage(PageMainRR.class);
	private Map<String, String> m = new LinkedHashMap<>();
	String point;
	SimpleDateFormat df;

	@BeforeMethod
	public void before1() {
		mPageMainRoot.getToolBar().doOpenWo();
		mPageWo.doEnter(EntrancesWo.SYSTEMSETUP);
		mPageSystemSetUp.doEnterPointList();
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void test(Map<String, String> param) {

		for (WebElement e : mPageSystemSetUp.getPointList()) {
			point = e.getAttribute("name");
			System.out.println(point);
			m.put("站点名称", point);
			mPageSystemSetUp.doChosePoint(e);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				mPageMainRoot.getToolBar().doBack();
				mPageMainRoot.getToolBar().doOpenTrade();
				if(param.get("账户类型").equals("A股")){
					mPageMainRoot.getToolBar().switchTo((byte) 0);
					mPageMainRoot.doEnter(EntrancesRoot.GPMR);
				}else if(param.get("账户类型").equals("融资融券")){
					mPageMainRoot.getToolBar().switchTo((byte) 1);
					mPageMainRR.doEnter(EntrancesRR.DBPMR);
					
				}
				mPage.dobeforeLogin(param.get("用户名"), param.get("密码"));
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				m.put("登录前", df.format(new Date()));
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				mPage.doLogin();
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				m.put("登录后", df.format(new Date()));
				mPageMainRoot.getToolBar().doBack();
				mPageMainRoot.doEnter(EntrancesRoot.LOGOUT);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mPage.doAccept();
				
				//e1.printStackTrace();
				//System.out.println("登录超时");
				//mPageMainRoot.getToolBar().doBack();
			
			
			/*mPageMainRoot.getToolBar().switchTo((byte) 1);
			mPageMainRR.doEnter(EntrancesRR.DBPMR);
			mPage.dobeforeLogin(param.get("融资融券用户名"), param.get("融资融券密码"));
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			m.put("登录前", df.format(new Date()));
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			mPage.doLogin();
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			m.put("登录后", df.format(new Date()));
			mPageMainRR.getToolBar().doBack();
			mPageMainRR.doEnter(EntrancesRoot.LOGOUT);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mPage.doAccept();*/
			
			mPageMainRoot.getToolBar().doOpenWo();
			mPageWo.doEnter(EntrancesWo.SYSTEMSETUP);
			mPageSystemSetUp.doEnterPointList();
		}
	}

	@AfterMethod
	public void after() {
		mPageMainRoot.getToolBar().doBack();
		mPageMainRoot.getToolBar().doBack();
	}
}
