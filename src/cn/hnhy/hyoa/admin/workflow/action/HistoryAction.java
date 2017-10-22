package cn.hnhy.hyoa.admin.workflow.action;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;

/**
 * 历史相关控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:43:07
 */
public class HistoryAction extends WorkflowAction {
	
	private static final long serialVersionUID = 8872801404601311072L;
	private List<HistoricTaskInstance> historicTaskInstances;
	private String processInstanceId;
	
	/** 查询历史任务 */
	public String selectHistoryTask(){
		try{
			historicTaskInstances = workflowService.getHistoricTaskInstance(processInstanceId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public List<HistoricTaskInstance> getHistoricTaskInstances() {
		return historicTaskInstances;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
