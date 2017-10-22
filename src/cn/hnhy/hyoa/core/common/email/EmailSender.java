package cn.hnhy.hyoa.core.common.email;
/**
 * 邮件发送接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:26:26
 */
public interface EmailSender {
	/**
	 * 发送邮件方法
	 * @param to 收件人
	 * @param subject 主题
	 * @param message 消息体
	 * @param html 是否为html格式的邮件 true：是  false: 不是
	 * @return true: 发送成功  false: 发送失败
	 */
	boolean send(String to, String subject, String message, boolean html);
}
