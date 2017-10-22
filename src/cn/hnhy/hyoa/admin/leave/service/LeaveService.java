package cn.hnhy.hyoa.admin.leave.service;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.leave.entity.LeaveAudit;
import cn.hnhy.hyoa.admin.leave.entity.LeaveItem;
import cn.hnhy.hyoa.core.common.web.PageModel;


/**
 * 假期管理业务处理接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:05:44
 */
public interface LeaveService {
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem
	 * @param pageModel
	 * @return List
	 */
	List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel);
	/**
	 * 加载假期类型
	 * @return List
	 */
	List<Map<String, Object>> loadLeaveType();
	/**
	 * 加载流程定义
	 * @return List
	 */
	List<Map<String, Object>> loadProcess();
	/**
	 * 加载假期类型 与 流程定义
	 * @return List
	 */
	List<List<Map<String, Object>>> loadLeaveTypeAndProcess();
	/**
	 * 填写用户请假单 
	 * @param leaveItem
	 */
	void saveLeaveItem(LeaveItem leaveItem);
	/**
	 * 查询当前登录用户需要审批的假期
	 * @return List
	 */
	List<LeaveItem> queryAuditLeave();
	/**
	 * 获取一个请假单
	 * @param id
	 * @return LeaveItem
	 */
	LeaveItem getLeaveItem(Long id);
	/**
	 * 审批假期
	 * @param taskId
	 * @param leaveAudit
	 */
	void audit(String taskId, LeaveAudit leaveAudit);
	/**
	 * 查询审批意见
	 * @param id
	 * @return List
	 */
	List<LeaveAudit> getLeaveAuditByLeaveItemId(Long id);

}
