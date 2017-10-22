package cn.hnhy.hyoa.admin.identity.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.core.common.web.PageModel;

/**
 * 用户控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月24日 下午9:53:28
 */
public class UserAction extends IdentityAction {
	
	private static final long serialVersionUID = -4517415497819844269L;
	/** 定义用户对象来封装查询条件 */
	private User user;
	private List<User> users;
	private String repwd;
	private String userIds;
	
	/** 多条件分页查询用户 */
	public String selectUser(){
		try{
			/** 解决get请求中文乱码 */
			if (user != null && !StringUtils.isEmpty(user.getName()) 
					&& ServletActionContext.getRequest()
						.getMethod().equalsIgnoreCase("get")){
				user.setName(new String(user.getName().getBytes("iso8859-1"), "utf-8"));
			}
			users = identityService.getUserByPage(user, pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加用户 */
	public String addUser(){
		try{
			identityService.saveUser(user);
			setUser(null);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到修改用户页面 */
	public String showUpdateUser(){
		try{
			user = identityService.getUser(user.getUserId(), false);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改用户 */
	public String updateUser(){
		try{
			/** 判断是不是修改Session中的用户 */
			if (StringUtils.isEmpty(user.getUserId())){
				user.setUserId(AdminConstant.getSessionUser().getUserId());
			}
			identityService.updateUser(user);
			if (AdminConstant.getSessionUser().getUserId().equals(user.getUserId())){
				ActionContext.getContext().getSession().put(AdminConstant.SESSION_USER, user);
			}
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除用户 */
	public String deleteUser(){
		try{
			identityService.deleteUser(userIds.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 批量审核用户 */
	public String checkUser(){
		try{
			identityService.checkUser(userIds.split(","), user.getStatus());
			setTip("审核成功！");
		}catch(Exception ex){
			setTip("审核失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	

	/** setter and getter method */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getUsers() {
		return users;
	}
	public String getRepwd() {
		return repwd;
	}
	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
}