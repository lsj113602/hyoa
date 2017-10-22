package cn.hnhy.hyoa.admin.interceptor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.admin.identity.service.IdentityService;
import cn.hnhy.hyoa.core.common.web.CookieTools;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录拦截器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月22日 下午10:32:46
 */
public class LoginInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -6595658029427493341L;
	@Resource
	private IdentityService identityService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/** 获取Session中的用户 */
		User user = (User)invocation.getInvocationContext()
				.getSession().get(AdminConstant.SESSION_USER);
		
		System.out.println("session_user: " + user);
		
		/** 定义标记 */
		boolean isLogin = false;
		
		if (user == null){
			/** 获取Cookie */
			Cookie cookie = CookieTools.getCookie(AdminConstant.COOKIE_NAME);
			if (cookie != null){
				/** 获取Cookie的值  userId(md5加密)*/
				String userId = cookie.getValue();
				/** 获取用户 */
				user = identityService.getUser(userId, true);
				if (user != null){
					/** 存入Session */
					invocation.getInvocationContext()
					 .getSession().put(AdminConstant.SESSION_USER, user);
					/** 根据用户id查询所有的角色，再根据角色查询所有的权限 */
					Map<String, List<String>> userAllPopedoms = identityService.getUserAllPopedoms(user.getUserId());
					/** 存入Session */
					invocation.getInvocationContext()
					 .getSession().put(AdminConstant.USER_ALL_POPEDOMS, userAllPopedoms);
				}else{
					isLogin = true;
				}
			}else{
				isLogin = true;
			}
		}
		
		return isLogin ? Action.LOGIN : invocation.invoke();
	}
}