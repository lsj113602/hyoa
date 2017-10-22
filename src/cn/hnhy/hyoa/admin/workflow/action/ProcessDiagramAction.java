package cn.hnhy.hyoa.admin.workflow.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 根据流程实例id查询流程图
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月19日 下午10:40:47
 */
public class ProcessDiagramAction extends ActionSupport{
	
	private static final long serialVersionUID = 6622861382287848668L;
	/** 定义流程实例id */
	private String processInstanceId;
	/** 获取仓储服务 */
	@Resource
	private RepositoryService repositoryService;
	/** 获取运行时服务 */
	@Resource
	private RuntimeService runtimeService;
	
	/** 显示流程图 */
	public String processDiagram() throws Exception {
		
		/** 根据流程实例id查询单条记录(RuntimeService) */
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
		/** 查询流程定义对应的图片(RepositoryService) */
		InputStream inputStream = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
		/** 把流程定义文件对应的图片转换成缓冲流图片 */
		BufferedImage image = ImageIO.read(inputStream);
		/** 获取画笔 */
		Graphics2D g = (Graphics2D)image.getGraphics();
		/** 设置画笔颜色 */
		g.setColor(Color.RED);
		/** 设置画笔的粗细 */
		g.setStroke(new BasicStroke(3.0f));
		/** 设置消除锯齿 */
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/** 查询流程定义文件(RepositoryService), 返回BpmnModel */
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		
		/** 根据流程实例id查询所有活动节点(RuntimeService) */
		List<String> activityIds = runtimeService.getActiveActivityIds(processInstanceId);
		/** 循环所有的活动节点 */
		for (String activitiId : activityIds){
			/** 
			 * 通过活动节点从BpmnModel对象中获取绘图信息 
			 * <omgdc:Bounds height="55.0" width="105.0" x="240.0" y="60.0"></omgdc:Bounds>
			 */
			GraphicInfo gi = bpmnModel.getGraphicInfo(activitiId);
			/** 绘制圆角的矩形 */
			g.drawRoundRect((int)gi.getX(), (int)gi.getY(),
						(int)gi.getWidth(), (int)gi.getHeight(), 10, 10);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		/** 设置响应的类型 */
		response.setContentType("images/png");
		/** 向浏览器输入图片 */
		ImageIO.write(image, "png", response.getOutputStream());
		return NONE;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}