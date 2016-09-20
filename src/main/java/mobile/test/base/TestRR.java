package mobile.test.base;

import mobile.preprocess.NeedPreprocess;
import mobile.preprocess.processor.LoginRR;
import mobile.preprocess.processor.OpenTrade;

/**
 * 融资融券交易测试基类
 */
@NeedPreprocess({OpenTrade.class, LoginRR.class})
public abstract class TestRR extends TestBase {

}
