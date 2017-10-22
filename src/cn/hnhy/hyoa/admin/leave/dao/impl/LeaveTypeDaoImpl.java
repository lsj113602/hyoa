package cn.hnhy.hyoa.admin.leave.dao.impl;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.leave.dao.LeaveTypeDao;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 假期类型数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:04:23
 */
public class LeaveTypeDaoImpl extends HibernateDaoImpl implements LeaveTypeDao {
	/**
	 * 查询假期类型(code与name)
	 * @return List
	 */
	public List<Map<String, Object>> getLeaveTypeByCodeAndName(){
		String hql = "select new map(code as code, name as name) from LeaveType order by code asc";
		return this.find(hql);
	}
}