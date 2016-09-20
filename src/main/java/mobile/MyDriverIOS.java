package mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import up.light.driver.IDriver;

public class MyDriverIOS implements IDriver {
	private IOSDriver<IOSElement> driver;

	public MyDriverIOS() {
		DesiredCapabilities dc = new DesiredCapabilities();
		// dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6
		// Plus");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.4");
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		dc.setCapability(MobileCapabilityType.APP,
				"/Users/csc108/Desktop/lixiang/app/0914/ZhongXinJianTou_Phone.app");
		// dc.setCapability(MobileCapabilityType.UDID,
		// "aa3b21769ee87f9e137a8a9eb4a69058afdce41f");
		dc.setCapability(MobileCapabilityType.NO_RESET, "true");
		dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
		// dc.setCapability(MobileCapabilityType.AUTO_WEBVIEW, "true");

		try {
			driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WebDriver> T getRealDriver() {
		return (T) driver;
	}

	@Override
	public String getCurrentContext() {
		return driver.getContext();
	}

	@Override
	public Set<String> getContexts() {
		return driver.getContextHandles();
	}

	@Override
	public void context(String context) {
		driver.context(context);
	}

	@Override
	public void tap(int x, int y, int duration) {
		driver.tap(1, x, y, duration);
	}

	@Override
	public void swipe(int startx, int starty, int endx, int endy, int duration) {
		driver.swipe(startx, starty, endx, endy, duration);
	}
	
}
