package cn.hnhy.hyoa.admin.employeeInfo.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.entity.Employee;


/**
 * 员工信息控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月9日 上午9:24:21
 */
public class EmployeeAction extends EmployeeInfoAction {
	private Employee employee;
	private List<Employee> employees;
	private String ids;
	private String employeeIds;
	private String tip;

	/** 分页查询员工 */
	public String selectEmployee(){
		try{
			employees = employeeInfoService.getEmployeeByPage(employee,pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**添加员工*/
	public String addEmployee(){
		try{
			employeeInfoService.addEmployee(employee);
			setEmployee(null);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**显示修改*/
	public String showUpdateEmployee(){
		try{
			employee = employeeInfoService.getEmployee(employee.getEmployeeId(), false);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 修改员工 */
	public String updateEmployee(){
		try{
			employeeInfoService.updateEmployee(employee);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除员工 */
	public String deleteEmployee(){
		try{
			employeeInfoService.deleteEmployee(employeeIds.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/*--------------geter and seter-------------*/
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
	
}
