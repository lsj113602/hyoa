package cn.hnhy.hyoa.admin;

import org.apache.struts2.ServletActionContext;



import cn.hnhy.hyoa.core.common.web.CookieTools;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户退出控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月4日 下午4:50:51
 */
public class AdminLogoutAction extends ActionSupport {
	
	private static final long serialVersionUID = 1768609517552880194L;

	@Override
	public String execute() throws Exception {
		/** 让Session失效 */
		ServletActionContext.getRequest().getSession().invalidate();
		/** 删除cookie */
		CookieTools.removeCookie(AdminConstant.COOKIE_NAME);
		return LOGIN;
	}
}
