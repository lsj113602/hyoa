package cn.hnhy.hyoa.admin.leave.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.leave.entity.LeaveItem;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;


/**
 * 假期明细数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:03:24
 */
public interface LeaveItemDao extends HibernateDao {
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem
	 * @param pageModel
	 * @return List
	 */
	List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel);

}
