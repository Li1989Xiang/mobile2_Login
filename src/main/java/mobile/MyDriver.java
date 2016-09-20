package mobile;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.TouchAction;
import io.selendroid.client.TouchActionBuilder;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.server.common.action.touch.FlickDirection;
import up.light.driver.IDriver;

public class MyDriver implements IDriver {
	private SelendroidDriver driver;

	public MyDriver() {
		SelendroidCapabilities c = SelendroidCapabilities.device("zhongxinjiantou.szkingdom.android.newphone");

		try {
			driver = new SelendroidDriver(c);
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WebDriver> T getRealDriver() {
		return (T) driver;
	}

	@Override
	public String getCurrentContext() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getContexts() {
		return driver.getContextHandles();
	}

	@Override
	public void context(String context) {
		driver.switchTo().window(context);
	}

	@Override
	public void swipe(int startx, int starty, int endx, int endy, int duration) {
		FlickDirection dir = null;
		int dis = 0;

		int valueX = endx - startx;
		int valueY = endy - starty;

		if (Math.abs(valueX) > Math.abs(valueY)) {
			if (valueX > 0) {
				dir = FlickDirection.RIGHT;
			} else {
				dir = FlickDirection.LEFT;
			}
			dis = Math.abs(valueX);
		} else {
			if (valueY > 0) {
				dir = FlickDirection.DOWN;
			} else {
				dir = FlickDirection.UP;
			}
			dis = Math.abs(valueY);
		}

		TouchAction ta = new TouchActionBuilder().flick(new Point(startx, starty), dir, dis, duration).build();
		ta.perform(driver);
	}
	
	@Override
	public void tap(int x, int y, int duration) {
//		TouchAction ta = new TouchActionBuilder().pointerDown(x, y).pause(duration).pointerUp().build();
//		ta.perform(driver);
		
		//TouchAction存在bug，点击多次之后会失效
		//使用adb shell input tap代替
		//！！！duration暂时无法实现！！！
		Runtime run = Runtime.getRuntime();
		String cmd = "D:/Program Files/adt-bundle-windows/sdk/platform-tools/adb.exe shell input tap ";
		
		try {
			Process p = run.exec(cmd + x + ' ' + y);
			p.waitFor();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
