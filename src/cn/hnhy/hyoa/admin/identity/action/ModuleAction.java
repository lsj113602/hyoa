package cn.hnhy.hyoa.admin.identity.action;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Module;


/**
 * 操作管理
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月24日 下午10:14:42
 */
public class ModuleAction extends IdentityAction {
	
	private static final long serialVersionUID = -687377726052135410L;
	private Module module;
	private List<Module> modules;
	private String parentCode;
	private String codes;
	
	/** 分页查询操作 */
	public String selectModule(){
		try{
			modules = identityService.getModuleByPage(parentCode, pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加操作 */
	public String addModule(){
		try{
			/** 设置父级code */
			module.setCode(parentCode);
			identityService.saveModule(module);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到修改操作页面 */
	public String showUpdateModule(){
		try{
			module = identityService.getModule(module.getCode());
			module.setName(module.getName().replaceAll("-", ""));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改操作 */
	public String updateModule(){
		try{
			identityService.updateModule(module);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 批量删除操作 */
	public String deleteModule(){
		try{
			identityService.deleteModule(codes.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
}
