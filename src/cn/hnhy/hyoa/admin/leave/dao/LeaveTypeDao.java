package cn.hnhy.hyoa.admin.leave.dao;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.core.dao.HibernateDao;


/**
 * 假期类型数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:03:15
 */
public interface LeaveTypeDao extends HibernateDao {
	/**
	 * 查询假期类型(code与name)
	 * @return List
	 */
	List<Map<String, Object>> getLeaveTypeByCodeAndName();

}
