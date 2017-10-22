package cn.hnhy.hyoa.admin.employeeInfo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.employeeInfo.dao.EmployeeDao;
import cn.hnhy.hyoa.admin.employeeInfo.service.EmployeeInfoService;
import cn.hnhy.hyoa.admin.identity.dao.DeptDao;
import cn.hnhy.hyoa.admin.identity.dao.JobDao;
import cn.hnhy.hyoa.admin.identity.entity.Dept;
import cn.hnhy.hyoa.admin.identity.entity.Employee;
import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.core.common.security.MD5;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.exception.OAException;

public class EmployeeInfoServiceImpl implements EmployeeInfoService {
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private DeptDao deptDao;
	/**
	 * 分页查询员工
	 */
	public List<Employee> getEmployeeByPage(Employee employee ,PageModel pageModel) {
		try{
			
			List<Employee> list = employeeDao.getEmployeeByPage(employee, pageModel);
			for(Employee e : list){
				/** 加载部门 */
				if (e.getDept() != null) e.getDept().getName();
				/** 加载职位 */
				if (e.getJob() != null) e.getJob().getName();
			}
			return list;
		}catch(Exception ex){
			throw new OAException("分页查询员工时出现了异常！", ex);
		}
	}
	/**
	 * 添加员工
	 * @param employee
	 * @return
	 */
	public void addEmployee(Employee employee){
		try{
			employee.setCreateDate(new Date());
			employeeDao.save(employee);
		}catch(Exception ex){
			throw new OAException("添加用员工出现异常！", ex);
		}
	}
	/**
	 * 根据员工id查询员工
	 * @param employeeId
	 * @param b
	 * @return
	 */
	public Employee getEmployee(Long employeeId, boolean isMD5){
		try{
			Employee employee = null;
			if (isMD5){
				employee = employeeDao.getEmployee(MD5.getMD5(employeeId+""));
			}else{
				employee = employeeDao.get(Employee.class, employeeId);
			}
			if (employee != null){
				if (employee.getDept() != null) employee.getDept().getId();
				if (employee.getJob() != null) employee.getJob().getCode();
			}
			return employee;
		}catch(Exception ex){
			throw new OAException("根据员工id查询员工时出现异常！", ex);
		}
	}
	
	/**
	 * 更新员工信息
	 * @param employee
	 */
	public void updateEmployee(Employee employee){
		try{
			/** 获取持久化状态用户 */
			Employee u = employeeDao.get(Employee.class, employee.getEmployeeId());
			u.setDept(employee.getDept());
			u.setEmail(employee.getEmail());
			u.setJob(employee.getJob());
			u.setName(employee.getName());
			u.setPhone(employee.getPhone());
			u.setSex(employee.getSex());
			u.setAddress(employee.getAddress());
			u.setIdcard(employee.getIdcard());
			u.setIdentity(employee.getIdentity());
			u.setJoinDate(employee.getJoinDate());
			u.setStatus(employee.getStatus());
			u.setCreateDate(employee.getCreateDate());
		}catch(Exception ex){
			throw new OAException("修改员工时出现异常！", ex);
		}
	}
	/**
	 * 批量删除员工
	 * @param split
	 */
	public void deleteEmployee(String[] employeeIds){
		/** 第三种方式 */
		try{
			employeeDao.deleteEmployee(employeeIds);
		}catch(Exception ex){
			throw new OAException("批量删除员工时出现异常！", ex);
		}
	}
	/** TODO############################# 职位管理业务处理 ################################## */
	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	public List<Job> getJobByPage(PageModel pageModel){
		try{
			List<Job> jobs = jobDao.getJobByPage(pageModel);
			return jobs;
		}catch(Exception ex){
			throw new OAException("分页查询职位时出现异常！", ex);
		}
	}
	/**
	 * 添加职位
	 * @param role
	 * @return List
	 */
	public void saveJob(Job job){
		try{
			jobDao.save(job);
		}catch(Exception ex){
			throw new OAException("添加职位时出现异常！", ex);
		}
	}
	/**
	 * 根据code获取职位
	 * @param code
	 * @return
	 */
	public Job getJob(String code){
		try{
			return jobDao.get(Job.class, code);
		}catch(Exception ex){
			throw new OAException("添加职位时出现异常！", ex);
		}
	}
	/**
	 * 修改职位
	 * @param job
	 * @return
	 */
	public void updateJob(Job job){
		try{
			Job j = jobDao.get(Job.class, job.getCode());
			j.setName(job.getName());
			j.setRemark(job.getRemark());
		}catch(Exception ex){
			throw new OAException("修改职位时出现异常！", ex);
		}
	}
	/**
	 * 批量删除职位
	 * @param split
	 */
	public void deleteJob(String[] codes){
		/** 第三种方式 */
		try{
			jobDao.deleteJob(codes);
		}catch(Exception ex){
			throw new OAException("批量删除职位时出现异常！", ex);
		}
	}
	/*################ 部门管理业务处理 #################*/
	/**
	 * 分页查询部门信息
	 * @param pageModel
	 * @return
	 */
	public List<Dept> getDeptByPage(PageModel pageModel){
		try{
			List<Dept> list = deptDao.getDeptByPage(pageModel);
			for(Dept e : list){
				/** 加载部门 */
				if (e.getCreater() != null) e.getCreater().getName();
				/** 加载职位 */
				if (e.getModifier() != null) e.getModifier().getName();
			}
			return list;
		}catch(Exception ex){
			throw new OAException("分页查询部门时出现了异常！", ex);
		}
	}
	/**
	 * 添加部门
	 */
	public void saveDept(Dept dept){
		try{
			dept.setCreateDate(new Date());
			dept.setCreater(AdminConstant.getSessionUser());
			deptDao.save(dept);
		}catch(Exception ex){
			throw new OAException("添加部门时出现异常！", ex);
		}
	}
	/**
	 * 根据id获取部门
	 * @param id
	 * @return
	 */
	public Dept getDept(Long id){
		try{
			return deptDao.get(Dept.class, id);
		}catch(Exception ex){
			throw new OAException("添加部门时出现异常！", ex);
		}
	}
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	public void updateDept(Dept dept){
		try{
			Dept j = deptDao.get(Dept.class, dept.getId());
			j.setModifyDate(new Date());
			j.setModifier(AdminConstant.getSessionUser());
			j.setName(dept.getName());
			j.setRemark(dept.getRemark());
		}catch(Exception ex){
			throw new OAException("修改部门时出现异常！", ex);
		}
	}
	/**
	 * 批量删除部门
	 * @param split
	 */
	public void deleteDept(String[] ids){
		/** 第三种方式 */
		try{
			deptDao.deleteDept(ids);
		}catch(Exception ex){
			throw new OAException("批量删除部门时出现异常！", ex);
		}
	}


}
