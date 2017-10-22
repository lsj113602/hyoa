package cn.hnhy.hyoa.admin.workflow.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.admin.workflow.service.WorkflowService;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.exception.OAException;

/**
 * 工作流业务处理接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:42:00
 */
public class WorkflowServiceImpl implements WorkflowService {
	
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private HistoryService historyService;
	
	/**
	 * 流程部署 
	 * @param name 流程定义说明名称
	 * @param bpmn 流程定义文件
	 * @param bpmnFileName 文件名
	 */
	public void processDeploy(String name, File bpmn, String bpmnFileName){
		try{
			/** 创建部署构建对象 */
			DeploymentBuilder builder = repositoryService.createDeployment();
			/** 添加说明名称 */
			builder.name(name);
			/** 添加分类 */
			builder.category(name);
			/** 添加流程定义文件 */
			builder.addInputStream(bpmnFileName, new FileInputStream(bpmn));
			/** 调用部署方法 */
			builder.deploy();
		}catch(Exception ex){
			throw new OAException("流程部署时出现了异常！", ex);
		}
	}
	/**
	 * 分页查询流程部署
	 * @param name 部署名称
	 * @param pageModel 分页实体
	 * @return 部署集合
	 */
	public List<Deployment> getDeploymentByPage(String name, PageModel pageModel){
		try{
			/** 创建部署查询对象 */
			DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
			/** 添加查询条件 */
			if (!StringUtils.isEmpty(name)){
				deploymentQuery.deploymentNameLike("%" + name + "%");
			}
			/** 做统计查询 */
			long recordCount = deploymentQuery.count();
			if (recordCount == 0){
				return null;
			}
			/** 按部署时间排序 */
			deploymentQuery.orderByDeploymenTime().asc();
			/** 为分页实体设置总记录条数 */
			pageModel.setRecordCount((int)recordCount);
			/** 做分页查询 */
			return deploymentQuery.listPage(pageModel.getStartRow(), pageModel.getPageSize());
		}catch(Exception ex){
			throw new OAException("分页查询流程部署时出现了异常！", ex);
		}
	}
	/**
	 * 删除流程部署
	 * @param ids
	 */
	public void deleteDeployment(String[] ids){
		try{
			for (String id : ids){
				repositoryService.deleteDeployment(id, true);
			}
		}catch(Exception ex){
			throw new OAException("删除流程部署时出现了异常！", ex);
		}
	}
	/**
	 * 查询历史任务
	 * @param processInstanceId
	 * @return List
	 */
	public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId){
		try{
			return historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId)
					.finished()
					.orderByTaskCreateTime()
					.asc()
					.list();
		}catch(Exception ex){
			throw new OAException("查询历史任务时出现了异常！", ex);
		}
	}
}
