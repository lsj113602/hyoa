package cn.hnhy.hyoa.admin.leave.action;

import java.util.List;

import cn.hnhy.hyoa.admin.leave.entity.LeaveAudit;


/**
 * 假期审批控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:32:14
 */
public class LeaveAuditAction extends LeaveAction {
	
	private static final long serialVersionUID = -8037213646282054314L;
	private LeaveAudit leaveAudit;
	private String taskId;
	private List<LeaveAudit> leaveAudits;
	
	/** 审批假期 */
	public String audit(){
		try{
			leaveService.audit(taskId, leaveAudit);
			setTip("审批成功！");
		}catch(Exception ex){
			setTip("审批失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 查询审批意见 */
	public String selectAuditResult(){
		try{
			leaveAudits = leaveService.getLeaveAuditByLeaveItemId(leaveAudit.getLeaveItem().getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public LeaveAudit getLeaveAudit() {
		return leaveAudit;
	}

	public void setLeaveAudit(LeaveAudit leaveAudit) {
		this.leaveAudit = leaveAudit;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<LeaveAudit> getLeaveAudits() {
		return leaveAudits;
	}

	public void setLeaveAudits(List<LeaveAudit> leaveAudits) {
		this.leaveAudits = leaveAudits;
	}
}
