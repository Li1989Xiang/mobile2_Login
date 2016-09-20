package mobile.result;

import java.util.List;
import java.util.Map;

import com.sapium.starter.IAfterEnd;
import com.sapium.writer.IWriter;
import com.sapium.writer.impl.ExcelWriter;

public class ReportWriter implements IAfterEnd {

	@Override
	public void doAfter() {
		Map<String, List<ResultBean>> vMap = ResultHolder.getmResultMap();
		IWriter vWriter = ExcelWriter.getInstance();
		
		for(Map.Entry<String, List<ResultBean>> e : vMap.entrySet()) {
			String vTestName = e.getKey();
			List<ResultBean> vList = e.getValue();
			
			for(ResultBean bean : vList) {
				Map<String, String> vToWrite = bean.getMap();
				vToWrite.put("testName", vTestName);
				vWriter.writeLine(vToWrite);
			}
		}
	}

}
