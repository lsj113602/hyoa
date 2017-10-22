package cn.hnhy.hyoa.admin.employeeInfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.admin.employeeInfo.dao.EmployeeDao;
import cn.hnhy.hyoa.admin.identity.entity.Employee;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

public class EmployeeDaoImpl extends HibernateDaoImpl implements EmployeeDao {

	/**
	 * 根据employeeId(加密)查询用户
	 * @param employeeId 用户id
	 * @return 用户
	 */
	public Employee getEmployee(String employeeId){
		String hql = "select u from Employee u where MD5(u.employeeId) = ?";
		return this.findUniqueEntity(hql, employeeId);
	}
	/**
	 * 多条件分页查询查询用户
	 * @param employee 查询条件
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<Employee> getEmployeeByPage(Employee employee, PageModel pageModel){
		StringBuilder hql = new StringBuilder();
		hql.append("select u from Employee u where 1=1 ");
		/** 定义List集合封装查询参数 */
		List<Object> params = new ArrayList<>();
		if (employee != null){
			/** 姓名 */
			if (!StringUtils.isEmpty(employee.getName())){
				hql.append(" and u.name like ?");
				params.add("%" + employee.getName() + "%");
			}
			/** 手机号码 */
			if (!StringUtils.isEmpty(employee.getPhone())){
				hql.append(" and u.phone like ?");
				params.add("%" + employee.getPhone() + "%");
			}
			/** 部门 */
			if (employee.getDept() != null && employee.getDept().getId() != null
					&& employee.getDept().getId() > 0){
				hql.append(" and u.dept.id = ?");
				params.add(employee.getDept().getId());
			}	
		}
		hql.append(" order by u.createDate asc");
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 查询员工姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> getEmployeeByName(String name){
		String hql = "select name from Employee where name like ?";
		return (List<String>)this.find(hql, new Object[]{"%" + name + "%"});
	}
	/**
	 * 批量删除员工
	 * @param employeeIds
	 */
	public void deleteEmployee(String[] employeeIds){
		// DELETE FROM `oa_id_employee` WHERE employee_id IN(?,?,?)
		// DML风格
		Long[] ids = new Long[employeeIds.length];
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Employee where employeeId in(");
		for (int i = 0; i < employeeIds.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			ids[i] = Long.parseLong(employeeIds[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), ids);
	}
	/**
	 * 批量审核用户
	 * @param employeeIds 审核的用户id
	 * @param status 状态码
	 */
	public void checkEmployee(String[] employeeIds, Short status){
		// UPDATE `oa_id_employee` SET check_date = ?, checker = ?, STATUS = ? WHERE employee_id IN(?,?,?)
		StringBuilder hql = new StringBuilder();
		hql.append("update Employee set  status = ? where employeeId in(");
		List<Object> params = new ArrayList<>();
		params.add(status);
		for (int i = 0; i < employeeIds.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			params.add(employeeIds[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), params.toArray());
	}
	

}
