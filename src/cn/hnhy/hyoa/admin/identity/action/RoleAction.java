package cn.hnhy.hyoa.admin.identity.action;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Role;


/**
 * 角色控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月23日 下午9:25:19
 */
public class RoleAction extends IdentityAction {
	
	private static final long serialVersionUID = -6318552760204345160L;
	private Role role;
	private List<Role> roles;
	private String ids;

	/** 分页查询角色 */
	public String selectRole(){
		try{
			roles = identityService.getRoleByPage(pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加角色 */
	public String addRole(){
		try{
			identityService.saveRole(role);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到修改角色页面 */
	public String showUpdateRole(){
		try{
			role = identityService.getRole(role.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改角色 */
	public String updateRole(){
		try{
			identityService.updateRole(role);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除角色 */
	public String deleteRole(){
		try{
			identityService.deleteRole(ids.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}