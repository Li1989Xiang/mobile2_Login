package mobile.listeners;

import org.testng.IConfigurationListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ShowError implements IConfigurationListener, ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
	}

	@Override
	public void onStart(ITestContext arg0) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		show(arg0);
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		show(arg0);
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		show(arg0);
	}

	@Override
	public void onTestStart(ITestResult arg0) {
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
	}

	@Override
	public void onConfigurationFailure(ITestResult arg0) {
		show(arg0);
	}

	@Override
	public void onConfigurationSkip(ITestResult arg0) {
		show(arg0);
	}

	@Override
	public void onConfigurationSuccess(ITestResult arg0) {
	}

	private void show(ITestResult arg0) {
		Throwable t = arg0.getThrowable();
		if(t != null) {
			System.err.println(t.getClass().getSimpleName() + ": " + t.getMessage());
//			t.printStackTrace();
		}
	}
}
