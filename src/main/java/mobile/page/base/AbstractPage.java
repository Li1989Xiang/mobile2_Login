package mobile.page.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import mobile.page.module.Alert;
import mobile.page.module.Keyboard;
import mobile.page.module.ToolBar;
import up.light.pagefactory.PageBase;
import up.light.utils.WaitUtil;

/**
 * 所有Page类的父类，提供成员变量自动初始化，统一异常处理等功能
 */
public class AbstractPage extends PageBase implements IResettable {
	// 网页空格ASCII码是160，但trim只能处理ASCII为32的空格
	private static final String STRIP_CHARS = new String(new char[] { 32, 160 });
	protected AbstractPageMain pageMain;

	/**
	 * 获取工具条
	 * @return
	 */
	public ToolBar getToolBar() {
		return PageManager.getPage(ToolBar.class);
	}

	/**
	 * 获取键盘
	 * @return
	 */
	public Keyboard getKeyboard() {
		return PageManager.getPage(Keyboard.class);
	}

	/**
	 * 获取对话框
	 * @return
	 */
	public Alert getAlert() {
		return PageManager.getPage(Alert.class);
	}

	/**
	 * 获取与之相关联的PageMain
	 * @return
	 */
	public AbstractPageMain getMain() {
		return pageMain;
	}

	/**
	 * 用于自动初始化pageMain字段，请勿手动调用
	 * @param pageMain
	 */
	void setMain(AbstractPageMain pageMain) {
		this.pageMain = pageMain;
	}
	
	/**
	 * 返回到根界面
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void backToRootMain() {
		ToolBar tb = getToolBar();
		AbstractPageMain main = pageMain;
		
		while(main != null) {
			tb.doBack();
			//修正LastUsed
			Class c = main.getClass();
			if(c.getSimpleName().indexOf("EnhancerByCGLIB") != -1) {
				c = c.getSuperclass();
			}
			PageManager.setLastUsed(c);
			
			main = main.getMain();
		}
	}
	

	/**
	 * 对于编辑框等输入控件获取其内容
	 * @param e
	 * @return
	 */
	protected String getValue(WebElement e) {
		return StringUtils.strip(e.getAttribute("value"), STRIP_CHARS);
	}

	/**
	 * 获取控件文本，对于输入类控件请使用{@link #getValue(WebElement)}
	 * @param e
	 * @return
	 */
	protected String getText(WebElement e) {
		return StringUtils.strip(e.getText(), STRIP_CHARS);
	}

	/**
	 * 统一异常处理：退出当前页面后重新进入。若有特殊要求可覆写此方法
	 */
	@Override
	public void reset() {
		if (pageMain != null) {
			getToolBar().doBack();
			WaitUtil.sleep(1000);
			pageMain.doLastEnter();
		}
	}
}
