package cn.hnhy.hyoa.admin.interceptor;

import java.util.List;
import java.util.Map;



import cn.hnhy.hyoa.admin.AdminConstant;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限拦截器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月4日 下午4:53:43
 */
public class PopedomInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -1908898358681816625L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/** 获取Action代理对象 */
		ActionProxy actionProxy = invocation.getProxy();
		/** 获取namespace命名空间 */
		String namespace = actionProxy.getNamespace();
		/** 获取action的name属性值 */
		String actionName = actionProxy.getActionName();
		/** 定义请求地址 */
		String requestUrl = namespace + "/" + actionName;
		
		/** /admin/identity/checkUser */
		System.out.println("当前用户请求URL: " + requestUrl);
		
		/** 从Session中获取当前用户所有的权限 */
		@SuppressWarnings("unchecked")
		Map<String, List<String>> userPopedomMaps = (Map<String, List<String>>)invocation
				.getInvocationContext()
				.getSession().get(AdminConstant.USER_ALL_POPEDOMS);
		
		/** 定义是否有权限访问 */
		boolean isPopedom = false;
		
		outer:
		for (Map.Entry<String, List<String>> entry : userPopedomMaps.entrySet()){
			/** 迭代Map集合中的value */
			for (String operaUrl : entry.getValue()){
				/** operaUrl : /admin/identity/checkUser */
				if (operaUrl.substring(0, operaUrl.lastIndexOf(".")).contains(requestUrl)){
					/** 为了控制页面上的按钮，需要把该模块所有的权限传到页面 */
					ActionContext.getContext().getSession().put(AdminConstant.USER_MODULE_POPEDOMS, entry.getValue());
					/** 有权限 */
					isPopedom = true;
					break outer;
				}
			}
		}
		
		if (isPopedom){
			return invocation.invoke();
		}else{
			invocation.getInvocationContext().put("tip", "亲，您的权限正在审核中，请稍候再试。。。！");
			return Action.ERROR;
		}
	}
}