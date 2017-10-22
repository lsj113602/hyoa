package cn.hnhy.hyoa.admin.leave.dao.impl;

import java.util.List;

import cn.hnhy.hyoa.admin.leave.dao.LeaveAuditDao;
import cn.hnhy.hyoa.admin.leave.entity.LeaveAudit;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 假期审批数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:04:57
 */
public class LeaveAuditDaoImpl extends HibernateDaoImpl implements LeaveAuditDao {
	/**
	 * 根据假期id查询审批意见
	 * @param id
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveAudit> getLeaveAuditByLeaveItemId(Long id){
		String hql = "select la from LeaveAudit la where la.leaveItem.id = ? order by la.id asc";
		return (List<LeaveAudit>)this.find(hql, new Object[]{id});
	}
}
