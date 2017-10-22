package cn.hnhy.hyoa.core.exception;
/**
 * 平台的基础异常
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-18 上午11:41:33
 * @version 1.0
 */
public class OAException extends RuntimeException {


	private static final long serialVersionUID = 7428372075170298879L;

	public OAException() {
	}
	public OAException(String message) {
		super(message);
	}
	public OAException(Throwable cause) {
		super(cause);
	}
	public OAException(String message, Throwable cause) {
		super(message, cause);
	}
	public OAException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}