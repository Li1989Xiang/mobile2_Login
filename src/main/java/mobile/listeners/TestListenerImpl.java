package mobile.listeners;

import java.util.Map;

import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.sapium.assertutil.AssertFailException;
import com.sapium.utils.ScreenShot;
import com.sapium.writer.impl.ExcelWriter;

import mobile.result.ReportWriter;
import mobile.result.ResultBean;
import mobile.result.ResultHolder;
import mobile.test.base.IRecovery;
import mobile.test.utils.StringCompartor;
import up.light.driver.DriverFactory;

public class TestListenerImpl implements ITestListener, IConfigurationListener {
	private static ExcelWriter mWriter;
	private static String mFolderName;

	@Override
	public void onTestStart(ITestResult result) {
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		record(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		record(result);
		handle(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		record(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		record(result);
		handle(result);
	}

	@Override
	public void onStart(ITestContext context) {
		mWriter = ExcelWriter.getInstance();
		mFolderName = mWriter.getFolder();
	}

	@Override
	public void onFinish(ITestContext context) {
		//解决报告不生成
		new ReportWriter().doAfter();
	}

	@Override
	public void onConfigurationFailure(ITestResult result) {
		handle(result);
	}

	@Override
	public void onConfigurationSkip(ITestResult result) {
	}

	@Override
	public void onConfigurationSuccess(ITestResult result) {
	}

	/*
	 * 异常处理
	 */
	private void handle(ITestResult result) {
		Throwable vThrow = result.getThrowable();
		if(vThrow != null) {
			Object vIns = result.getMethod().getInstance();
			if(vIns instanceof IRecovery) {
				IRecovery vTestIns = (IRecovery)vIns;
				try {
					vTestIns.recovery();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/*
	 * 生成测试报告
	 */
	@SuppressWarnings("unchecked")
	private void record(ITestResult result) {
		if(result == null)
			return;
		
		String name = result.getTestClass().getRealClass().getSimpleName();
		
		if("Test00Wait".equals(name))
			return;
		
		String vClassName = result.getInstance().getClass().getSimpleName();
		String vTestName = result.getTestContext().getCurrentXmlTest().getParameter(vClassName);
		String vCaseName = getInvokeNumber(result.getMethod().getCurrentInvocationCount());
		int vResult = result.getStatus();
		
		if(vClassName.equals(result.getTestContext().getAttribute("SKIP"))) {
			vResult = ITestResult.SKIP;
		}
		
		String vStatus = status2String(vResult);
		String vScreenShot = null;
		
		if(vResult == ITestResult.FAILURE || vResult == ITestResult.SUCCESS_PERCENTAGE_FAILURE) {
			vScreenShot = "screenshot/" + vClassName + vCaseName + ".png";
			ScreenShot.take(DriverFactory.getDriver().getRealDriver(), mFolderName + vScreenShot);
		}
		
		Map<String, String> vParamMap = null;
		Object[] vArrayParam = result.getParameters();
		
		if(vArrayParam.length > 0) {
			vParamMap = (Map<String, String>)(vArrayParam[0]);
		}
		
		String vParamStr = null;
		if(vParamMap != null) {
			vParamStr = param2String(vParamMap);
		}
		
		Throwable vEx = result.getThrowable();
		String vExpectStr = null;
		String vActualStr = null;
		
		if(vEx != null) {
			if(vEx instanceof AssertFailException) {
				AssertFailException ve = (AssertFailException)vEx;
				vActualStr = ve.getActual().toString();
				if(vParamMap != null) {
					vExpectStr = ve.getExpect().toString();
					vActualStr = StringCompartor.differenceWithArrow(vActualStr, vExpectStr, "->");
				}
			} else {
				vActualStr = vEx.getMessage();
				
				if(vActualStr != null) {
					int vIndex = vActualStr.indexOf("Build info: ");
					
					if(vIndex >= 0)
						vActualStr = vActualStr.substring(0, vIndex);
				}
			}
		}
		
		/*Map<String, String> vMap = new LinkedHashMap<>();
		vMap.put("testName", vTestName);
		vMap.put("caseName", vCaseName);
		vMap.put("status", vStatus);
		vMap.put("parameter", vParamStr);
		vMap.put("expect", vExpectStr);
		vMap.put("actual", vActualStr);
		vMap.put("screenShot", vScreenShot);
		
		mWriter.writeLine(vMap);*/
		ResultBean vBean = new ResultBean(vCaseName, vStatus, vParamStr, vExpectStr, vActualStr, vScreenShot);
		ResultHolder.putResult(vTestName, vBean);
	}

	private String status2String(int status) {
		switch(status) {
		case ITestResult.SUCCESS:
			return "Pass";
		case ITestResult.FAILURE:
			return "Fail";
		case ITestResult.SKIP:
			return "Skip";
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
			return "PartFail";
		default:
			return "Unknown";
		}
	}

	private String getInvokeNumber(int i) {
		if(i < 10) {
			return "00" + i;
		} else if(i < 100) {
			return "0" + i;
		} else {
			return String.valueOf(i);
		}
	}
	
	private String param2String(Map<String, String> param) {
		StringBuilder vSb = new StringBuilder();
		String vTmp = null;
		
		for(Map.Entry<String, String> e : param.entrySet()) {
			vTmp = e.getKey();
			if(vTmp.contains("验证") || vTmp.contains("委托编号"))
				continue;
			vSb.append(vTmp + ":" + e.getValue() + "\n");
		}
		
		if(vSb.length() > 0)
			vSb.deleteCharAt(vSb.length() - 1);
		
		String vNo = param.get("委托编号");
		if(vNo != null) {
			vSb.append("\n------------\n");
			vSb.append("委托编号：" + vNo);
		}
		
		return vSb.toString();
	}
}
