package cn.hnhy.hyoa.admin.leave.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.leave.entity.LeaveAudit;
import cn.hnhy.hyoa.core.dao.HibernateDao;


/**
 * 假期审批数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:03:34
 */
public interface LeaveAuditDao extends HibernateDao {
	/**
	 * 根据假期id查询审批意见
	 * @param id
	 * @return List
	 */
	List<LeaveAudit> getLeaveAuditByLeaveItemId(Long id);

}