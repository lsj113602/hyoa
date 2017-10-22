package cn.hnhy.hyoa.admin.leave.action;

import java.util.List;

import cn.hnhy.hyoa.admin.leave.entity.LeaveItem;


/**
 * 假期明细控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:32:25
 */
public class LeaveItemAction extends LeaveAction {
	
	private static final long serialVersionUID = 2856283845071034027L;
	private LeaveItem leaveItem;
	private List<LeaveItem> leaveItems;
	
	
	/** 分页查询当前登录用户的请假单 */
	public String selectUserLeave(){
		try{
			leaveItems = leaveService.getUserLeaveByPage(leaveItem, pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 填写用户请假单 */
	public String addLeaveItem(){
		try{
			leaveService.saveLeaveItem(leaveItem);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 查询当前登录用户需要审批的假期  */
	public String selectAuditLeave(){
		try{
			leaveItems = leaveService.queryAuditLeave();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到审批假期的页面 */
	public String showAudit(){
		try{
			/** 定义一个变量缓存任务id */
			String taskId = leaveItem.getTaskId();
			leaveItem = leaveService.getLeaveItem(leaveItem.getId());
			leaveItem.setTaskId(taskId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/** setter and getter method */
	public LeaveItem getLeaveItem() {
		return leaveItem;
	}
	public void setLeaveItem(LeaveItem leaveItem) {
		this.leaveItem = leaveItem;
	}
	public List<LeaveItem> getLeaveItems() {
		return leaveItems;
	}
	public void setLeaveItems(List<LeaveItem> leaveItems) {
		this.leaveItems = leaveItems;
	}
}