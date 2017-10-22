package cn.hnhy.hyoa.admin.addressbook.dao.impl;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.addressbook.dao.ContactGroupDao;
import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 联系组数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午7:54:18
 */
public class ContactGroupDaoImpl extends HibernateDaoImpl implements ContactGroupDao {
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return 联系组集合
	 */
	public List<ContactGroup> getContactGroupByPage(PageModel pageModel){
		String hql = "select cg from ContactGroup cg order by id asc";
		return this.findByPage(hql, pageModel, null);
	}
	/**
	 * 加载联系组
	 * @return
	 */
	public List<Map<String, Object>> getContactGroupByIdAndName(){
		String hql = "select new map(id as id, name as name) from ContactGroup order by id asc";
		return this.find(hql);
	}
}
