package cn.hnhy.hyoa.admin.leave.action;

import java.util.List;
import java.util.Map;

/**
 * 假期管理异步请求控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:31:56
 */
public class LeaveAjax extends LeaveAction {
	
	private static final long serialVersionUID = -858022810666135070L;
	private List<Map<String, Object>> responseData;
	private List<List<Map<String, Object>>> responseLists;
	
	/** 异步加载假期类型 */
	public String loadLeaveTypeAjax(){
		try{
			responseData = leaveService.loadLeaveType();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载假期类型 与 流程定义 */
	public String loadLeaveTypeAndProcessAjax(){
		try{
			responseLists = leaveService.loadLeaveTypeAndProcess();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	
	
	public List<List<Map<String, Object>>> getResponseLists() {
		return responseLists;
	}
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
}
