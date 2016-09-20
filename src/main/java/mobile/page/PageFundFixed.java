package mobile.page;

import java.util.List;

import com.sapium.page.WaitConst;

import io.appium.java_client.MobileElement;
import mobile.page.base.AbstractPage;
import mobile.page.base.SetMain;
import up.light.utils.Conditions;
import up.light.utils.WaitUtil;

/** 
 *基金定投与定赎封装界面
 */
@SetMain(PageMainJJ.class)
public class PageFundFixed extends AbstractPage{

	private MobileElement oTextDT;//基金定投
	private MobileElement oTextDS;//基金定赎
	
	private MobileElement oTextSQ;//申请新的定投计划
	
	private MobileElement oEditCode;//输入基金代码
	private MobileElement oTextName;//基金名称
	private MobileElement oTextPrice;//基金净值
	private MobileElement oEditNumber;//输入定投金额
	private MobileElement oBtnTime;//扣款周期
	private MobileElement oTextMonth;//月
	private MobileElement oTextWeek;//周
	private List<MobileElement> oMenuMonth;//扣款周期：月
	private List<MobileElement> oMenuWeek;//扣款周期：周
	private MobileElement oBtnDescription;//投资描述
	private List<MobileElement> oMenuDescription;//投资描述选择项
	private MobileElement oBtnCondition;//期满条件
	private List<MobileElement> oMenuCondition;//期满条件选择项
	private MobileElement oBtnMark;//巨额标记
	private List<MobileElement> oMenuMark;//巨额标记
	private MobileElement oEditBeginTime;//开始日期
	private MobileElement oEditEndTime;//终止日期
	private MobileElement oEditQMCS;//期满次数
	private MobileElement oEditQMJE;//期满金额
	private MobileElement oBtnSG;//申购按钮
	private MobileElement oMsgRisk;//风险提示
	private MobileElement oImgLoad;//请等待...

	
	//涉及多次使用，通过Singleton实例化
	//	private PageFundFixed() {}


	
	/**
	 * 点击基金定投或者基金定赎
	 */
	public void doChooseDTorDS(String type){
        MobileElement vToClick = null;
		
		switch(type){
		case"基金定投":
			vToClick = oTextDT;
			//vToClick = oTextDS;
			break;
		case"基金定赎":
			vToClick = oTextDS;
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}
		WaitUtil.waitFor(driver, vToClick, WaitUtil.WAIT_LONG);
		vToClick.click();
		while(WaitUtil.exists(driver, oImgLoad, 1)){
			WaitUtil.sleep(1000);
		}
	}
	
	public void doAddSG(){
		WaitUtil.sleep(2000);
		oTextSQ.click();
		if(WaitUtil.exists(driver, oTextSQ, WaitUtil.WAIT_SHORT*3)) {
		}

	}
	
	/**
	 * 输入基金代码,并获取基金名称
	 * @param code 基金代码
	 */
	public String doInputCode(String code) {
		WaitUtil.sleep(1000);
		//等待并点击代码编辑框
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG);
		driver.getRealDriver().navigate().refresh();
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_LONG);
		//输入代码并等待数据加载
		getKeyboard().doInput(oEditCode, code);
		//WaitUtil.waitForText(driver, oTextName, WaitUtil.WAIT_LONG, null, Conditions.NOTBLANK);
		String vTestName = getText(oTextName);
		while(vTestName.equals("")){
			int x = 0;
			if(x > 3){
				break;
			}
			WaitUtil.sleep(1000);
			vTestName = getText(oTextName);
			x++;
		} 
		return vTestName;
	}
	
	public void doClear(){
		
		oTextPrice.clear();
	}
	/**
	 * 获取基金净值编辑框中的文本
	 * @return 基金净值文本
	 */
	public String doGetPrice() {
		return getText(oTextPrice);
	}
	
	/**
	 * 输入定投金额or每期定赎份额
	 * @param number 
	 * @param number 定投金额
	 */
	public void doInputNumber(String number) {
	
		WaitUtil.sleep(1000);
		String numberValue=getText(oEditNumber);
		
		if(numberValue.equals("请输入定赎份额") || numberValue.equals("请输入定投金额")){
			
			getKeyboard().doInput(oEditNumber, number);
		}else{
			
			WaitUtil.sleep(1000);
			oEditNumber.click();
			oEditNumber.clear();
			getKeyboard().doInput(oEditNumber, number);
			
		}
	
	}
	
	
	/**
	 * 扣款周期选择，月或者日
	 */
	public void doChooseTime(String type,String time){
		WaitUtil.sleep(3000);
		oBtnTime.click();
    
		switch(type){
		case"每月":
			doChooseMonth(time) ;
			break;
		case"每周":
			doChooseWeek(time);
			break;
		default:
			throw new RuntimeException(type + " is unsupported");
		}
	}
	
    /**
     * 选择月
     * @param month
     */
	public void doChooseMonth(String month) {
		boolean vFlag = false;
		WaitUtil.sleep(1000);
		oTextMonth.click();
	
		WaitUtil.sleep(1000);
		
		for(MobileElement e : oMenuMonth) {
			if(getText(e).equals(month)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find month: " + month);
		}
	}
	
	/**
     * 选择周
     * @param week
     */
	public void doChooseWeek(String week) {
		boolean vFlag = false;
		
		oTextWeek.click();
		WaitUtil.sleep(1000);
		for(MobileElement e : oMenuWeek) {
			if(getText(e).equals(week)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find week: " + week);
		}
	}
	
	/**
     * 选择投资描述
     * @param descriptionk  
     */
	public void doChooseDescription(String description) {
		boolean vFlag = false;
		
		oBtnDescription.click();
		WaitUtil.sleep(1000);
		
		for(MobileElement e : oMenuDescription) {
			if(getText(e).equals(description)) {
				WaitUtil.sleep(1000);
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find description: " + description);
		}
	}
	
	/**
     * 选择期满条件
     * @param condition  
     */
	public void doChooseCondition(String condition) {
		boolean vFlag = false;
		
		oBtnCondition.click();
		WaitUtil.sleep(1000);
		
		for(MobileElement e : oMenuCondition) {
			
			if(getText(e).equals(condition)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find condition: " + condition);
		}
	}
	
	public void doChooseMark(String mark){
        boolean vFlag = false;
		
		oBtnMark.click();
		WaitUtil.sleep(1000);
	
		for(MobileElement e : oMenuMark) {
	
			if(getText(e).equals(mark)) {
				e.click();
				vFlag = true;
				break;
			}
		}
		
		if(!vFlag) {
			throw new RuntimeException("can't find condition: " + mark);
		}
		
	}
	/**
	 * 获取开始日期
	 * @param beginTime
	 */
	public String doGetBeginTime() {
		String vbeginTime="";
		if(WaitUtil.exists(driver,oEditBeginTime,WaitConst.SHORT)){
		  //vbeginTime= getText(oEditBeginTime);
		  vbeginTime= getValue(oEditBeginTime);
		}
		return vbeginTime;
	}
	/**
	 * 获取结束日期
	 * @param endTime
	 */
	public String doGetEndTime() {
		String vendTime="";
		if(WaitUtil.exists(driver,oEditBeginTime,WaitConst.SHORT)){
			//vendTime= getText(oEditEndTime);
			vendTime= getValue(oEditEndTime);
		}
		return vendTime;
	}
	/**
	 * 输入期满次数或者期满金额
	 * @param num
	 * @param pric
	 */
	public void doInputCondition(String num,String pric){
		if(WaitUtil.exists(driver,oEditQMCS,WaitConst.SHORT)){
			getKeyboard().doInput(oEditQMCS, num);
		}
		if(WaitUtil.exists(driver,oEditQMJE,WaitConst.SHORT)){
			getKeyboard().doInput(oEditQMJE, pric);
		}
		
	}
	public void doSG(){
		WaitUtil.sleep(1000);
		//refresh();
		oBtnSG.click();
	}
	
	public void doHandleRiskAlert(){
		if(WaitUtil.exists(driver,oMsgRisk, 1)) {
			getAlert().doAccept();
		}
	}
	
	
	@Override
	public void reset() {
		if (pageMain != null) {
			getToolBar().doBack();
			WaitUtil.sleep(1000);
			oTextSQ.click();
		}
	}
}
