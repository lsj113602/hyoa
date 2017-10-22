package cn.hnhy.hyoa.admin.addressbook.action;

import javax.annotation.Resource;




import cn.hnhy.hyoa.admin.addressbook.service.AddressbookService;
import cn.hnhy.hyoa.core.common.web.PageModel;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 通讯录基础控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月2日 下午10:51:59
 */
public class AddressbookAction extends ActionSupport {
	
	private static final long serialVersionUID = 8207601938482922521L;
	/** 注入通讯录业务处理接口 */
	@Resource
	protected AddressbookService addressbookService;
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