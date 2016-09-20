package mobile.test.base;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.sapium.configuration.TestConfiguration;
import com.sapium.dataprovider.DataIterator;
import com.sapium.resource.IDataResource;
import com.sapium.resource.impl.FileResouce;

public class TestDataProvider {
	private static IDataResource mResource;
	
	@DataProvider(name="dp")
	public static DataIterator data(ITestContext t, Method m) {
		String vClassName = m.getDeclaringClass().getSimpleName();
		String vSheetName = t.getCurrentXmlTest().getParameter(vClassName);
		
		if(mResource == null) {
			String p = TestConfiguration.getPlatform().getName() + "/";
			mResource = new FileResouce(p + "data/data.xls");
		}
		
		return new DataIterator(mResource, vSheetName);
	}
}
