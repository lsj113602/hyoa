package cn.hnhy.hyoa.admin.leave.action;

import javax.annotation.Resource;




import cn.hnhy.hyoa.admin.leave.service.LeaveService;
import cn.hnhy.hyoa.core.common.web.PageModel;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 假期管理基础控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:31:40
 */
public class LeaveAction extends ActionSupport {

	private static final long serialVersionUID = 8203258751230675594L;
	/** 注入业务层接口 */
	@Resource
	protected LeaveService leaveService;
	protected PageModel pageModel = new PageModel();
	private String tip;
	
	/** setter and getter method */
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
}