package cn.hnhy.hyoa.admin.addressbook.dao;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 联系组数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午7:55:30
 */
public interface ContactGroupDao extends HibernateDao {
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return 联系组集合
	 */
	List<ContactGroup> getContactGroupByPage(PageModel pageModel);
	/**
	 * 加载联系组
	 * @return
	 */
	List<Map<String, Object>> getContactGroupByIdAndName();

}