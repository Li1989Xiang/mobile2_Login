package mobile.preprocess;

import java.util.HashMap;
import java.util.Map;

/**
 * 预处理抽象类
 */
public abstract class Preprocessor {
	private static final FailStrategy DEFAULT_STRATEGY = FailStrategy.STOP;
	private static Map<Class<? extends Preprocessor>, PreprocessResult> mResultMap = new HashMap<>();
	private static Map<Class<? extends Preprocessor>, FailStrategy> mStrategyMap = new HashMap<>();
	
	/**
	 * 为process方法提供运行所需参数
	 * @param clazz 当前测试类
	 * @return 参数数组
	 */
	public abstract Object[] getParameter();
	
	/**
	 * 执行预处理操作
	 * @param param 运行参数
	 */
	public abstract void process(Object[] param);
	
	/**
	 * 恢复到原始状态
	 */
	public abstract void recovery();
	
	public Preprocessor() {
		mStrategyMap.put(this.getClass(), getStrategy());
	}
	
	/**
	 * 覆写此方法可修改默认的预处理失败处理策略，默认为STOP
	 */
	protected FailStrategy getStrategy() {
		return DEFAULT_STRATEGY;
	}
	
	/**
	 * 获取预处理结果
	 * @param clazz Preprocessor的实现类
	 * @return 预处理结果
	 */
	public static PreprocessResult getResult(Class<? extends Preprocessor> clazz) {
		PreprocessResult vResult = mResultMap.get(clazz);
		
		if(vResult == null) {
			vResult = PreprocessResult.NOTPROCESS;
			mResultMap.put(clazz, vResult);
		}
		
		return vResult;
	}

	/**
	 * 设值预处理结果
	 * @param clazz Preprocessor的实现类
	 * @param result 预处理结果
	 */
	public static void setResult(Class<? extends Preprocessor> clazz, PreprocessResult result) {
		mResultMap.put(clazz, result);
	}
	
	/**
	 * 预处理失败时默认的处理策略为停止后续执行
	 * @param clazz Preprocessor的实现类
	 * @return 处理策略
	 */
	public static FailStrategy getStrategy(Class<? extends Preprocessor> clazz) {
		FailStrategy vRet = mStrategyMap.get(clazz);
		
		if(vRet == null)
			vRet = DEFAULT_STRATEGY;
		
		return vRet;
	}
}
