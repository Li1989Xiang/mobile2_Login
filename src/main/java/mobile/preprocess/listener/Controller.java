package mobile.preprocess.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.IConfigurable;
import org.testng.IConfigureCallBack;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import mobile.preprocess.FailStrategy;
import mobile.preprocess.NeedPreprocess;
import mobile.preprocess.PreprocessResult;
import mobile.preprocess.Preprocessor;
import mobile.preprocess.RunBeforeAll;
import mobile.test.base.TestPT;
import mobile.test.base.TestRR;
import up.light.utils.ClassUtil;

/**
 * <ul>
 * <li>拦截Test方法和Configuration方法，根据注解{@link NeedPreprocess}进行预处理，并根据预处理结果决定是否进行后续执行</li>
 * <li>使用时需在TestNG中注册</li>
 * </ul>
 */
public class Controller implements IHookable, IConfigurable {
	//仅当FailStrategy设置为STOP且预处理执行失败时才会设置该成员变量
	private Class<?> mLastFailClass;
	private Map<Class<?>, Boolean> mHasRunned = new HashMap<>();
	
	public Controller() {
		mHasRunned.put(TestPT.class, false);
		mHasRunned.put(TestRR.class, false);
	}
	
	/**
	 * 拦截Test方法，并进行预处理
	 */
	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		Class<?> vClazz = testResult.getTestClass().getRealClass();
		NeedPreprocess vAnno = vClazz.getAnnotation(NeedPreprocess.class);
		
		if(vAnno != null) {
			//类标记为需要预处理
			//上次失败的类与本次相同，则直接跳过后面所有测试方法
			if(vClazz == mLastFailClass) {
				setSkip(testResult, vClazz.getSimpleName());
				return;
			}
			
			Class<? extends Preprocessor>[] vArr = vAnno.value();
			
			//break label
			LoopEnd: for(Class<? extends Preprocessor> c : vArr) {
				//获取之前处理的结果
				PreprocessResult vResult = Preprocessor.getResult(c);
				
				switch (vResult) {
				case NOTPROCESS:
					if(runProcessor(c) == false) {
						//执行失败
						setSkip(testResult, vClazz.getSimpleName());
						//根据失败策略进行处理，仅在STOP时设置mLastFailClass
						if(FailStrategy.STOP == Preprocessor.getStrategy(c)) {
							mLastFailClass = vClazz;
							break LoopEnd;
						}
					}
					break;
				case FAIL:
					//this should be never invoked
					if(FailStrategy.STOP == Preprocessor.getStrategy(c)) {
						mLastFailClass = vClazz;
						break LoopEnd;
					}
					break;
				default:
					break;
				}
			}//end of for
			
			if(vClazz != mLastFailClass) {
				//如果没有发生错误则执行Test方法
				callBack.runTestMethod(testResult);
			}
		} else {
			//不需预处理直接放行
			callBack.runTestMethod(testResult);
		}
	}

	/**
	 * 拦截Configuration方法
	 */
	@Override
	public void run(IConfigureCallBack callBack, ITestResult testResult) {
		Class<?> vClazz = testResult.getTestClass().getRealClass();
		NeedPreprocess vAnno = vClazz.getAnnotation(NeedPreprocess.class);
		Class<?> vSuper = getTestSuperClass(vClazz);
		
		if(vAnno != null) {
			//类标记为需要预处理
			//上次失败的类与本次相同，则直接跳过后面所有测试方法
			if(vClazz == mLastFailClass) {
				setSkip(testResult, vClazz.getSimpleName());
				return;
			}
			
			Class<? extends Preprocessor>[] vArr = vAnno.value();
			Class<? extends Preprocessor> vClazzFail = hasFail(vArr);
			
			//存在失败
			if(vClazzFail != null) {
				if(Preprocessor.getStrategy(vClazzFail) == FailStrategy.STOP) {
					mLastFailClass = vClazz;
					return;
				}
			}
			
			List<Class<? extends Preprocessor>> vToRun = new ArrayList<>(3);
			
			LoopEnd : for(Class<? extends Preprocessor> c : vArr) {
				//第一次运行Configuration方法
				if(mHasRunned.get(vSuper) == false) {
					if(isRunBeforeAll(c)) {
						vToRun.add(c);
					}
				} else {
					//只添加未运行的预处理器
					PreprocessResult vResult = Preprocessor.getResult(c);
					
					switch (vResult) {
					case NOTPROCESS:
						vToRun.add(c);
						break;
					case FAIL:
						//this should be never invoked
						if(FailStrategy.STOP == Preprocessor.getStrategy(c)) {
							mLastFailClass = vClazz;
							break LoopEnd;
						}
						break;
					default:
						break;
					}
				}
			}//end of for
			
			//执行预处理器
			for(Class<? extends Preprocessor> c : vToRun) {
				if(runProcessor(c) == false) {
					//执行失败
					setSkip(testResult, vClazz.getSimpleName());
					//根据失败策略进行处理，仅在STOP时设置mLastFailClass
					if(FailStrategy.STOP == Preprocessor.getStrategy(c)) {
						mLastFailClass = vClazz;
					}
					break;
				}
			}
			
			if(vClazz != mLastFailClass) {
				//如果没有发生错误则执行Test方法
				callBack.runConfigurationMethod(testResult);
			}
		} else {
			//不需预处理直接放行
			callBack.runConfigurationMethod(testResult);
		}
		
		mHasRunned.put(vSuper, true);
	}
	
	private boolean runProcessor(Class<? extends Preprocessor> clazz) {
		Preprocessor vProcessor = null;
		boolean vRet = true;
		
		try {
			//执行预处理
			vProcessor = ClassUtil.getInstance(clazz);
			vProcessor.process(vProcessor.getParameter());
			//设置执行结果
			Preprocessor.setResult(clazz, PreprocessResult.SUCCESS);
		} catch (Exception e) {
			//预处理失败
			e.printStackTrace();
			Preprocessor.setResult(clazz, PreprocessResult.FAIL);
			vRet = false;
			//恢复原始状态，使后续任务能继续执行
			if(vProcessor != null)
				vProcessor.recovery();
		}
		
		return vRet;
	}
	
	private Class<? extends Preprocessor> hasFail(Class<? extends Preprocessor>[] arr) {
		for(Class<? extends Preprocessor> c : arr) {
			if(Preprocessor.getResult(c) == PreprocessResult.FAIL)
				return c;
		}
		
		return null;
	}
	
	private boolean isRunBeforeAll(Class<? extends Preprocessor> clazz) {
		return clazz.getAnnotation(RunBeforeAll.class) != null;
	}
	
	private void setSkip(ITestResult testResult, String className) {
		testResult.getTestContext().setAttribute("SKIP", className);
	}
	
	private Class<?> getTestSuperClass(Class<?> clazz) {
		if(TestPT.class.isAssignableFrom(clazz)) {
			return TestPT.class;
		}
		
		if(TestRR.class.isAssignableFrom(clazz)) {
			return TestRR.class;
		}
		
		return null;
	}
}
