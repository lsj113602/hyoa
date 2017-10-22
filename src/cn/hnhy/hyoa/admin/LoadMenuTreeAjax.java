package cn.hnhy.hyoa.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import cn.hnhy.hyoa.admin.identity.service.IdentityService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 异步根据当前登录用户生成功能菜单树
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-28 上午10:31:33
 * @version 1.0
 */
public class LoadMenuTreeAjax extends ActionSupport {
	
	private static final long serialVersionUID = -301730378782429934L;
	@Resource
	private IdentityService identityService;
	private List<Map<String, Object>> responseData;
	
	@Override
	public String execute() {
		try{
			responseData = identityService.loadMenuTree();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
}
