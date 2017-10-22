package cn.hnhy.hyoa.admin;

import javax.annotation.Resource;



import cn.hnhy.hyoa.admin.identity.service.IdentityService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 修改密码的控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:46:23
 */
public class UpdatePwdAction extends ActionSupport {
	
	private static final long serialVersionUID = -489274302990964623L;
	private String oldpwd;
	private String newpwd;
	private String okpwd;
	@Resource
	private IdentityService identityService;
	private String tip;
	
	@Override
	public String execute() {
		try{
			boolean isSuccess = identityService.updatePwd(oldpwd, newpwd, okpwd);
			if (isSuccess){
				return SUCCESS;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		tip = "密码修改失败！";
		return INPUT;
	}

	/** setter and getter method */
	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getOkpwd() {
		return okpwd;
	}

	public void setOkpwd(String okpwd) {
		this.okpwd = okpwd;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
