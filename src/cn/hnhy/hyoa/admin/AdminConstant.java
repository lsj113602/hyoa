package cn.hnhy.hyoa.admin;


import cn.hnhy.hyoa.admin.identity.entity.User;

import com.opensymphony.xwork2.ActionContext;

/**
 * 常量类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月23日 下午9:08:21
 */
public final class AdminConstant {
	
	/** 定义存放在Session中的用户 */
	public static final String SESSION_USER = "session_user";
	/** 定义Cookie的有效时间(按秒计算) 7天 */
	public static final int MAX_AGE = 60 * 60 * 24 * 7;
	/** 定义Cookie的名称 */
	public static final String COOKIE_NAME = "oa_user_cookie";
	/** 定义存放在Session中用户的所有权限 */
	public static final String USER_ALL_POPEDOMS = "user_all_popedoms";
	/** 定义存放在Session中用户每一个模块的权限 */
	public static final String USER_MODULE_POPEDOMS = "user_module_popedoms";
	
	/** 获取当前Session中的用户 */
	public static User getSessionUser() {
		return (User)ActionContext.getContext().getSession().get(SESSION_USER);
	}
	
	
}
