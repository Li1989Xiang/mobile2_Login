package mobile.test.base;

import mobile.preprocess.NeedPreprocess;
import mobile.preprocess.processor.LoginPT;
import mobile.preprocess.processor.OpenTrade;

/**
 * 普通交易测试基类
 */
@NeedPreprocess({OpenTrade.class, LoginPT.class})
public abstract class TestPT extends TestBase {

}
