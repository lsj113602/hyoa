package cn.hnhy.hyoa.admin.workflow.action;

import java.io.File;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * 部署管理控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:40:24
 */
public class DeploymentAction extends WorkflowAction {
	
	private static final long serialVersionUID = -4473213547546651898L;
	private String name;
	/** 文件上传 */
	private File bpmn;
	private String bpmnFileName;
	private List<Deployment> deployments;
	private String ids;
	
	/** 流程部署 */
	public String processDeploy(){
		try{
			workflowService.processDeploy(name, bpmn, bpmnFileName);
			setTip("部署成功！");
		}catch(Exception ex){
			setTip("部署失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** 分页查询流程部署 */
	public String selectDeployment(){
		try{
			if (ServletActionContext.getRequest().getMethod().equalsIgnoreCase("get") &&
					!StringUtils.isEmpty(name)){
				name = new String(name.getBytes("iso8859-1"), "utf-8");
			}
			deployments = workflowService.getDeploymentByPage(name, pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除流程部署 */
	public String deleteDeployment(){
		try{
			workflowService.deleteDeployment(ids.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** setter and getter method */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getBpmn() {
		return bpmn;
	}
	public void setBpmn(File bpmn) {
		this.bpmn = bpmn;
	}
	public String getBpmnFileName() {
		return bpmnFileName;
	}
	public void setBpmnFileName(String bpmnFileName) {
		this.bpmnFileName = bpmnFileName;
	}
	public List<Deployment> getDeployments() {
		return deployments;
	}
	public void setDeployments(List<Deployment> deployments) {
		this.deployments = deployments;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}