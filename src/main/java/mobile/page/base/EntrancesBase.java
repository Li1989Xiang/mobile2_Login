package mobile.page.base;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import up.light.utils.ArgumentUtil;
import up.light.utils.WaitUtil;

public class EntrancesBase implements IEntryPoint {
	private static Map<String, EntrancesBase> entrances = new HashMap<>();
	public WebElement element;
	
	public EntrancesBase(String name) {
		entrances.put(name, this);
	}
	
	public static EntrancesBase fromString(String name) {
		EntrancesBase e = entrances.get(name);
		ArgumentUtil.notNull(e, "can't find entrance with name: " + name);
		return e;
	}

	@Override
	public void doEnter() {
		ArgumentUtil.notNull(element, "Element of entrance is null");
		element.click();
		WaitUtil.sleep(1000);
	}

	public void setElement(WebElement element) {
		this.element = element;
	}

}
