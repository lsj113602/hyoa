package cn.hnhy.hyoa.admin.workflow.service;

import java.io.File;
import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;

import cn.hnhy.hyoa.core.common.web.PageModel;

/**
 * 工作流业务处理接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:41:46
 */
public interface WorkflowService {
	/**
	 * 流程部署 
	 * @param name 流程定义说明名称
	 * @param bpmn 流程定义文件
	 * @param bpmnFileName 文件名
	 */
	void processDeploy(String name, File bpmn, String bpmnFileName);
	/**
	 * 分页查询流程部署
	 * @param name 部署名称
	 * @param pageModel 分页实体
	 * @return 部署集合
	 */
	List<Deployment> getDeploymentByPage(String name, PageModel pageModel);
	/**
	 * 删除流程部署
	 * @param ids
	 */
	void deleteDeployment(String[] ids);
	/**
	 * 查询历史任务
	 * @param processInstanceId
	 * @return List
	 */
	List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId);

}
