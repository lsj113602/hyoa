package cn.hnhy.hyoa.admin.leave.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.admin.leave.dao.LeaveAuditDao;
import cn.hnhy.hyoa.admin.leave.dao.LeaveItemDao;
import cn.hnhy.hyoa.admin.leave.dao.LeaveTypeDao;
import cn.hnhy.hyoa.admin.leave.entity.LeaveAudit;
import cn.hnhy.hyoa.admin.leave.entity.LeaveItem;
import cn.hnhy.hyoa.admin.leave.service.LeaveService;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.exception.OAException;

/**
 * 假期管理业务处理接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:45:44
 */
public class LeaveServiceImpl implements LeaveService {
	/** 组合该模块所有的数据访问接口，实现该模块所有的业务处理 */
	@Resource
	private LeaveTypeDao leaveTypeDao;
	@Resource
	private LeaveItemDao leaveItemDao;
	@Resource
	private LeaveAuditDao leaveAuditDao;
	@Resource
	private RepositoryService repositoryService; 
	@Resource
	private RuntimeService runtimeService; 
	@Resource
	private TaskService taskService; 
	@Resource
	private HistoryService historyService; 
	
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem
	 * @param pageModel
	 * @return List
	 */
	public List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel){
		try{
			List<LeaveItem> leaveItems = leaveItemDao.getUserLeaveByPage(leaveItem, pageModel);
			/** 加载延迟属性 */
			for (LeaveItem li : leaveItems){
				if (li.getCreater() != null) li.getCreater().getName();
				if (li.getLeaveType() != null) li.getLeaveType().getName();
			}
			return leaveItems;
		}catch(Exception ex){
			throw new OAException("分页查询当前登录用户的请假单时出现异常！", ex);
		}
	}
	/**
	 * 加载假期类型
	 * @return List
	 */
	public List<Map<String, Object>> loadLeaveType(){
		try{
			return leaveTypeDao.getLeaveTypeByCodeAndName();
		}catch(Exception ex){
			throw new OAException("加载假期类型时出现异常！", ex);
		}
	}
	/**
	 * 加载流程定义
	 * @return List
	 */
	public List<Map<String, Object>> loadProcess(){
		try{
			/** 创建流程定义查询对象 */
			List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				             .latestVersion()
				             .orderByDeploymentId()
				             .asc()
				             .list();
			List<Map<String, Object>> data = new ArrayList<>();
			for (ProcessDefinition pd : processDefinitions){
				Map<String, Object> map = new HashMap<>();
				map.put("id", pd.getId());
				map.put("name", pd.getName());
				data.add(map);
			}
			return data;
		}catch(Exception ex){
			throw new OAException("加载假期类型时出现异常！", ex);
		}
	}
	/**
	 * 加载假期类型 与 流程定义
	 * @return List
	 */
	public List<List<Map<String, Object>>> loadLeaveTypeAndProcess(){
		try{
			// [[{},{}],[{},{}]] : List<List<Map>>
			List<List<Map<String, Object>>> data = new ArrayList<List<Map<String, Object>>>();
			/** 添加假期类型 */
			data.add(loadLeaveType());
			/** 添加流程定义 */
			data.add(loadProcess());
			return data;
		}catch(Exception ex){
			throw new OAException("加载假期类型 与 流程定义时出现异常！", ex);
		}
	}
	/**
	 * 填写用户请假单 
	 * @param leaveItem
	 */
	public void saveLeaveItem(LeaveItem leaveItem){
		try{
			/** 获取流程定义id */
			String processDefId = leaveItem.getProcInstanceId();
			/** 获取当前登录的用户 */
			User user = AdminConstant.getSessionUser();
			/** 定义流程变量 */
			Map<String, Object> map = new HashMap<>();
			map.put("jobCode", Integer.valueOf(user.getJob().getCode()));
			map.put("deptId", user.getDept().getId());
			/** 根据流程定义id开启流程实例 */
			ProcessInstance pi = runtimeService.startProcessInstanceById(processDefId, map);
			
			leaveItem.setCreateDate(new Date());
			leaveItem.setCreater(user);
			/** 让请假单记住它属于哪个流程实例 */
			leaveItem.setProcInstanceId(pi.getId());
			/** 保存请假单 */
			leaveItemDao.save(leaveItem);
			
			/** 让流程实例记住它属于哪个请假单 */
			runtimeService.setVariable(pi.getId(), "leaveId", leaveItem.getId());
			
		}catch(Exception ex){
			throw new OAException("填写用户请假单时出现异常！", ex);
		}
	}
	/**
	 * 查询当前登录用户需要审批的假期
	 * @return List
	 */
	public List<LeaveItem> queryAuditLeave(){
		try{
			/** 获取当前登录的用户 */
			User user = AdminConstant.getSessionUser();
			/** 根据候选组查询所有需要领取的任务 */
			List<Task> taskLists = taskService.createTaskQuery()
					.taskCandidateGroup(user.getJob().getCode()).list();
			/** 迭代所有的任务 */
			for (Task task : taskLists){
				/** 获取部门流程变量 */
				long deptId = taskService.getVariable(task.getId(), "deptId", Long.class);
				/** 判断该任务是不是自己部门的任务(每个任务都对应一个流程实例) */
				if (user.getDept().getId() == deptId || "0009".equals(user.getJob().getCode())){
					/** 当前用户领取该任务 */
					taskService.claim(task.getId(), user.getUserId());
				}
			}
			/** 根据任务处理人查询所有的任务 */
			taskLists = taskService.createTaskQuery().taskAssignee(user.getUserId()).list();
			/** 定义请假单的集合 */
			List<LeaveItem> leaveItems = new ArrayList<LeaveItem>();
			/** 循环所有的任务转化成请假单 */
			for (Task task : taskLists){
				/** 获取请假单id的流程变量 */
				long leaveId = taskService.getVariable(task.getId(), "leaveId", Long.class);
				/** 获取请假单 */
				LeaveItem leaveItem = leaveItemDao.get(LeaveItem.class, leaveId);
				/** 加载延迟的属性 */
				if (leaveItem != null){
					if (leaveItem.getCreater() != null) leaveItem.getCreater().getName();
					if (leaveItem.getLeaveType() != null) leaveItem.getLeaveType().getName();
				}
				/** 设置任务ID到请假单 */
				leaveItem.setTaskId(task.getId());
				/** 添加请假单 */
				leaveItems.add(leaveItem);
			}
			return leaveItems;
		}catch(Exception ex){
			throw new OAException("查询当前登录用户需要审批的假期时出现异常！", ex);
		}
	}
	/**
	 * 获取一个请假单
	 * @param id
	 * @return LeaveItem
	 */
	public LeaveItem getLeaveItem(Long id){
		try{
			LeaveItem leaveItem = leaveItemDao.get(LeaveItem.class, id);
			if (leaveItem != null){
				if (leaveItem.getCreater() != null) leaveItem.getCreater().getName();
			}
			return leaveItem;
		}catch(Exception ex){
			throw new OAException("获取一个请假单时出现异常！", ex);
		}
	}
	/**
	 * 审批假期
	 * @param taskId
	 * @param leaveAudit
	 */
	public void audit(String taskId, LeaveAudit leaveAudit){
		try{
			/** 用Map集合封装流程变量 */
			Map<String, Object> map = new HashMap<>();
			map.put("status", leaveAudit.getStatus());
			/** 完成任务 */
			taskService.complete(taskId, map);
			
			/** 保有审批意见 */
			leaveAudit.setCheckDate(new Date());
			leaveAudit.setChecker(AdminConstant.getSessionUser());
			leaveAuditDao.save(leaveAudit);
			
			/** 查询请假单 */
			LeaveItem leaveItem = getLeaveItem(leaveAudit.getLeaveItem().getId());
			/** 根据流程实例id查询历史流程实例 */
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(leaveItem.getProcInstanceId()).singleResult();
			/** 判断流程实例是不是完成了 */
			if (hpi.getEndTime() != null){
				/** 修改请假单的审批状态  0:新建1:审核通过2:不通过 */
				leaveItem.setStatus(leaveAudit.getStatus() == 0 ? (short)2 : (short)1);
			}
			
		}catch(Exception ex){
			throw new OAException("审批假期时出现异常！", ex);
		}
	}
	/**
	 * 查询审批意见
	 * @param id
	 * @return List
	 */
	public List<LeaveAudit> getLeaveAuditByLeaveItemId(Long id){
		try{
			List<LeaveAudit> leaveAudits = leaveAuditDao.getLeaveAuditByLeaveItemId(id);
			for (LeaveAudit leaveAudit : leaveAudits){
				if (leaveAudit.getChecker() != null) {
					leaveAudit.getChecker().getName();
					leaveAudit.getChecker().getJob().getName();
				}
			}
			return leaveAudits;
		}catch(Exception ex){
			throw new OAException("查询审批意见时出现异常！", ex);
		}
	}
}
