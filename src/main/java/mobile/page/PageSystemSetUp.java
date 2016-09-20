package mobile.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.ArgumentUtil;
import up.light.utils.WaitUtil;

/**
 * 银证转账
 * @category Stage-01
 * @since 2016-08-01
 */
@SetMain(PageWo.class)
public class PageSystemSetUp extends AbstractPage {
	private WebElement oBtnChangePoint;
	private WebElement oBtnOK;
	private List<WebElement> oListPoint;
	

	/**
	 * 进入选择站点页面
	 */
	public void doEnterPointList() {
		oBtnChangePoint.click();
	}

	public void doChosePoint(WebElement e){
		e.click();
		oBtnOK.click();
	}
	
	public List<WebElement> getPointList(){
		return oListPoint;
	}
}
