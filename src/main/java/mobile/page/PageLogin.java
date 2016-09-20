package mobile.page;

import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import mobile.page.module.Keyboard;
import up.light.utils.WaitUtil;

/**
 * 普通交易、融资融券登录
 * @category Reused
 * @since 2016-08-01
 */
@SetMain(PageMainRoot.class)
public class PageLogin extends AbstractPage {
	//登录界面元素
	private WebElement oEditUser;
	private WebElement oEditPwd;
	private WebElement oEditVerCode;
	private WebElement oTextVerCode;
	private WebElement oBtnLogin;
	private WebElement oBtnOK;
	private WebElement oBtnMakeSure;
	
	/**
	 * 根据给定的用户名、密码登录
	 * @param username 用户名
	 * @param password 密码
	 */
	public void dobeforeLogin(String username, String password) {
		//等待用户名编辑框出现
		WaitUtil.waitFor(driver, oEditUser, 10);
		
		Keyboard kb = getKeyboard();
		
		//点击用户名编辑框并输入
		if(!getValue(oEditUser).equals(username)) {
			kb.doInput(oEditUser,username);
		}
		
		//点击密码编辑框并输入
		kb.doInput(oEditPwd,password);
		
		//输入验证码
		String vCode = oTextVerCode.getText();
		kb.doInput(oEditVerCode,vCode);
		
	}
	
	public void doLogin(){
		//点击登录
		oBtnLogin.click();
		if(WaitUtil.exists(driver, oBtnOK, WaitUtil.WAIT_SHORT*2)){
			oBtnOK.click();
		}
		//等待登录按钮消失
		WaitUtil.untilGone(driver, oBtnLogin, WaitUtil.WAIT_LONG * 2);
		
	}
	
	public void doAccept(){
		oBtnMakeSure.click();
	}
	
	/*
	 * 因需要处理位于股票选择界面的异常，所以覆写父类reset方法
	 */
	@Override
	public void reset() {
		// 位于股票买卖界面
		super.reset();
	}
}
