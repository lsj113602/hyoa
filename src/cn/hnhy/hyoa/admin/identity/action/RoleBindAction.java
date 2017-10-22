package cn.hnhy.hyoa.admin.identity.action;

import java.util.List;

import org.apache.struts2.json.JSONUtil;

import cn.hnhy.hyoa.admin.identity.entity.Module;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.admin.identity.entity.User;

/**
 * 角色绑定控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月3日 下午9:56:04
 */
public class RoleBindAction extends IdentityAction {
	
	private static final long serialVersionUID = -4323715876746449910L;
	private Role role;
	private List<User> users;
	private String userIds;
	private List<Module> modules;
	private String moduleCode;
	private String codes;
	private String operaCodes;

	/** 根据角色id分页查询用户(把这个角色中的用户查询出来) */
	public String selectRoleUser(){
		try{
			users = identityService.getUserByPageAndRoleId(role.getId(), pageModel);
			role = identityService.getRole(role.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来) */
	public String showBindUser(){
		try{
			pageModel.setPageSize(6);
			users = identityService.getUserByPageNotRoleId(role.getId(), pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 角色绑定用户 */
	public String bindUser(){
		try{
			identityService.bindUser(role.getId(), userIds.split(","));
			setTip("绑定成功！");
		}catch(Exception ex){
			setTip("绑定失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 角色解除用户 */
	public String unBindUser(){
		try{
			identityService.unBindUser(role.getId(), userIds.split(","));
			setTip("解除成功！");
		}catch(Exception ex){
			setTip("解除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 查询权限(12位操作) */
	public String selectPopedom(){
		try{
			modules = identityService.getModulesByCode8(moduleCode);
			role = identityService.getRole(role.getId());
			/** 查询该角色在这个模块中已经绑定的操作 , JSONUtil.serialize方法把List<String>集合序列成js中的数组格式的字符串 */
			operaCodes = JSONUtil.serialize(identityService.getOperaCodeByRoleIdAndModuleCode(role.getId(), moduleCode));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 角色绑定操作 */
	public String bindModule(){
		try{
			identityService.bindModule(role, moduleCode, codes);
			setTip("绑定成功！");
		}catch(Exception ex){
			setTip("绑定失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** setter and getter method */
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getOperaCodes() {
		return operaCodes;
	}
}