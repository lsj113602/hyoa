package cn.hnhy.hyoa.admin.identity.action;

import java.util.List;
import java.util.Map;



/**
 * 权限管理模块异步加载数据
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月10日 下午11:08:24
 */
public class IdentityAjax extends IdentityAction {
	
	private static final long serialVersionUID = 31117673719793208L;
	private List<Map<String, Object>> responseData;
	private List<String> nameData;
	private String name;
	private Map<String, List<Map<String,Object>>> deptJobResponseData;
	private boolean userIdExistData;
	private String userId;
	
	/** 异步加载部门 */
	public String loadDeptAjax(){
		try {
			// [{id : 1, name : '技术部'},{id : 1, name : '技术部'},...]
			// [] : List
			// {} : Map
			// [{},{}] : List<Map>
			responseData = identityService.loadDept();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载用户姓名 */
	public String loadNameAjax(){
		try {
			// ['','']
			// [] : List<String>
			nameData = identityService.loadName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载部门与职位 */
	public String loadDeptAndJobAjax(){
		try {
			// {"depts" : [{id : 1, name : ''},{id : 1, name : ''}], "jobs" : [{code : '', name : ''},{}]}
			// Map<String, List<Map<String,Object>>>
			deptJobResponseData = identityService.loadDeptAndJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步验证用户名是否重复 */
	public String validUserIdAjax(){
		try {
			// data: true|false
			userIdExistData = identityService.validUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载操作树 */
	public String loadModuleTreeAjax(){
		try {
			// data: [{id : '', pid: '', name : ''},{},...]
			responseData = identityService.loadModuleTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 异步加载权限树 */
	public String loadPopedomTreeAjax(){
		try {
			// data: [{id : '', pid: '', name : ''},{},...]
			responseData = identityService.loadPopedomTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
	public List<String> getNameData() {
		return nameData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, List<Map<String, Object>>> getDeptJobResponseData() {
		return deptJobResponseData;
	}
	public boolean getUserIdExistData() {
		return userIdExistData;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
