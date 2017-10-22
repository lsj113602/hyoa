package cn.hnhy.hyoa.admin.identity.action;

import javax.annotation.Resource;

import cn.hnhy.hyoa.admin.identity.service.IdentityService;
import cn.hnhy.hyoa.core.common.web.PageModel;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 权限管理模块基础的控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月24日 下午9:52:18
 */
public class IdentityAction extends ActionSupport {
	private static final long serialVersionUID = 7009546403270584768L;
	@Resource
	protected IdentityService identityService;
	/** 定义分页实体 */
	protected PageModel pageModel = new PageModel();
	/** 定义提示信息 */
	private String tip;
	
	/** setter and getter method */
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
}