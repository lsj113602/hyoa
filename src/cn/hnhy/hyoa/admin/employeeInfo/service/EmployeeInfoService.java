package cn.hnhy.hyoa.admin.employeeInfo.service;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Dept;
import cn.hnhy.hyoa.admin.identity.entity.Employee;
import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.core.common.web.PageModel;

/**
 * 人事信息管理业务接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月9日 上午9:14:17
 */
public interface EmployeeInfoService {
	/**
	 * 分页查询员工
	 * @param pageModel
	 * @return
	 */
	List<Employee> getEmployeeByPage(Employee employee,PageModel pageModel);

	/**
	 * 添加员工
	 * @param employee
	 * @return
	 */
	void addEmployee(Employee employee);
	/**
	 * 根据员工id查询员工
	 * @param employeeId
	 * @param b
	 * @return
	 */
	Employee getEmployee(Long employeeId, boolean b);
	/**
	 * 更新员工信息
	 * @param employee
	 */
	void updateEmployee(Employee employee);
	/**
	 * 批量删除员工
	 * @param split
	 */
	void deleteEmployee(String[] employeeIds);

	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	List<Job> getJobByPage(PageModel pageModel);
	
	/**
	 * 添加职位
	 * @param job
	 */
	void saveJob(Job job);
	/**
	 * 根据code获取职位
	 * @param code
	 * @return
	 */
	Job getJob(String code);

	/**
	 * 修改职位
	 * @param job
	 * @return
	 */
	void updateJob(Job job);

	/**
	 * 批量删除职位
	 * @param split
	 */
	void deleteJob(String[] codes);

	/**
	 * 分页查询部门信息
	 * @param pageModel
	 * @return
	 */
	List<Dept> getDeptByPage(PageModel pageModel);

	/**
	 * 添加部门
	 * @param dept
	 */
	void saveDept(Dept dept);
	
	/**
	 * 根据id获取部门
	 * @param id
	 * @return
	 */
	Dept getDept(Long id);
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	void updateDept(Dept dept);
	
	/**
	 * 批量删除部门
	 * @param split
	 */
	void deleteDept(String[] ids);
	
}
