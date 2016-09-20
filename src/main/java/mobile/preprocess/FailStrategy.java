package mobile.preprocess;

/**
 * 预处理失败处理策略
 */
public enum FailStrategy {
	STOP,		//停止后续所有执行
	CONTINUE;	//忽略失败继续执行
}
