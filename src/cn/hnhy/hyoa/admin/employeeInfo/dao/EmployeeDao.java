package cn.hnhy.hyoa.admin.employeeInfo.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Employee;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 员工数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月9日 上午8:54:38
 */
public interface EmployeeDao extends HibernateDao {
	/**
	 * 根据employeeId(加密)查询员工
	 * @param employeeId 员工id
	 * @return 员工
	 */
	Employee getEmployee(String employeeId);
	/**
	 * 多条件分页查询查询员工
	 * @param employee 查询条件
	 * @param pageModel 分页实体
	 * @return 员工集合
	 */
	List<Employee> getEmployeeByPage(Employee employee, PageModel pageModel);
	/**
	 * 查询员工姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	List<String> getEmployeeByName(String name);
	/**
	 * 批量删除员工
	 * @param employeeIds
	 */
	void deleteEmployee(String[] employeeIds);
	/**
	 * 批量审核员工状态
	 * @param employeeIds 审核的员工id
	 * @param status 状态码
	 */
	void checkEmployee(String[] employeeIds, Short status);
	
}
