package cn.hnhy.hyoa.admin.workflow.action;

import javax.annotation.Resource;




import cn.hnhy.hyoa.admin.workflow.service.WorkflowService;
import cn.hnhy.hyoa.core.common.web.PageModel;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 工作流基础控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:42:37
 */
public class WorkflowAction extends ActionSupport {
	
	private static final long serialVersionUID = -8353261704175352225L;
	@Resource
	protected WorkflowService workflowService;
	protected PageModel pageModel = new PageModel();
	private String tip;
	
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
