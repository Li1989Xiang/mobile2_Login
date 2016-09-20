package mobile.test.utils;

import com.sapium.starter.IAfterEnd;
import com.sapium.starter.IBeforeStart;

public class DBCheckHelper implements IBeforeStart, IAfterEnd {

	@Override
	public void doAfter() {
		DBCheck.getInstance().close();
	}

	@Override
	public void doBeofre() {
		DBCheck.getInstance();
	}

}
