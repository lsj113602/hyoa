package cn.hnhy.hyoa.core.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志切面类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-24 下午4:09:54
 * @version 1.0
 */
public class LogAdvice {
	
	/** 获取日志记录对象 */
	private Logger logger = Logger.getLogger(LogAdvice.class);
	
	/** 记录业务层方法的性能(执行的时间) */
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		/** 获取当前时间毫秒数 */
		long beginMillis = System.currentTimeMillis();
		logger.info("开始调用【"+ pjp.getSignature().getName() +"】方法，开始时间毫秒数为：" + beginMillis);
		System.out.println("=======调用目标方法之前=========");
		/** 调用目标方法 */
		Object res = pjp.proceed(pjp.getArgs());
		System.out.println("=======调用目标方法之后=========");
		long endMillis = System.currentTimeMillis();
		logger.info("结束调用【"+ pjp.getSignature().getName() +"】方法，结束时间毫秒数为：" + endMillis);
		logger.info("调用【"+ pjp.getSignature().getName() +"】方法，一共花费了:" + (endMillis - beginMillis) + "毫秒！");
		logger.info("调用【"+ pjp.getSignature().getName() +"】方法，返回值为：" + res);
		return res;
	}
	
	/** 记录业务层方法的异常信息 */
	public void error(JoinPoint jp, Throwable ex){
		logger.info("调用【"+ jp.getSignature().getName() +"】方法，出现异常！");
		logger.error(ex.getMessage(), ex);
	}
	
}
