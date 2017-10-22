package cn.hnhy.hyoa.admin.employeeInfo.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;

import cn.hnhy.hyoa.admin.employeeInfo.service.EmployeeInfoService;
import cn.hnhy.hyoa.core.common.web.PageModel;

public class EmployeeInfoAction extends ActionSupport  {
	
	private static final long serialVersionUID = 7334320769795179691L;
	@Resource
	protected EmployeeInfoService employeeInfoService;
	/** 定义分页实体 */
	protected PageModel pageModel = new PageModel();
	/** 定义提示信息 */
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
