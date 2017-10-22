package cn.hnhy.hyoa.admin.employeeInfo.action;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Dept;

public class DeptAction extends EmployeeInfoAction {

	private List<Dept> depts;
	private Dept dept;
	private String ids;
	
	/** 分页查部门 */
	public String selectDept(){
		try{
			depts = employeeInfoService.getDeptByPage(pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**添加部门*/
	public String addDept(){
		try{
			employeeInfoService.saveDept(dept);
			dept = null;
			setTip("添加成功！");
		}catch(Exception ex){
			ex.printStackTrace();
			setTip("添加失败！");
		}
		return SUCCESS;
	}
	/**显示修改*/
	public String showUpdateDept(){
		try{
			dept = employeeInfoService.getDept(dept.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改部门 */
	public String updateDept(){
		try{
			employeeInfoService.updateDept(dept);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 删除部门 */
	public String deleteDept(){
		try{
			employeeInfoService.deleteDept(ids.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/*--------------geter and seter-------------*/

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
}
