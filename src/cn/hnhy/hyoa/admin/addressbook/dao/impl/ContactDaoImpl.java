package cn.hnhy.hyoa.admin.addressbook.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.addressbook.dao.ContactDao;
import cn.hnhy.hyoa.admin.addressbook.dao.ContactGroupDao;
import cn.hnhy.hyoa.admin.addressbook.entity.Contact;
import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 联系人数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午7:53:53
 */
public class ContactDaoImpl extends HibernateDaoImpl implements ContactDao {
	/**
	 * 分页查询联系人
	 * @param contact 联系人实体
	 * @param pageModel 分页实体
	 * @return 联系人集合
	 */
	public List<Contact> getContactByPage(Contact contact, PageModel pageModel){
		StringBuilder hql = new StringBuilder();
		hql.append("select c from Contact c where 1=1 ");
		/** 定义List集合封装查询条件 */
		List<Object> params = new ArrayList<Object>();
		if (contact != null){
			/** 联系组 */
			if (contact.getContactGroup() != null 
					&& contact.getContactGroup().getId() != null
					&& contact.getContactGroup().getId() > 0){
				hql.append(" and c.contactGroup.id = ?");
				params.add(contact.getContactGroup().getId());
			}
		}
		hql.append(" order by c.id asc");
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 删除联系人
	 * @param ids
	 */
	public void deleteContact(String[] ids){
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Contact where id in(");
		Long[] params = new Long[ids.length];
		for (int i = 0; i < ids.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			params[i] = Long.valueOf(ids[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), params);
	}
	/**
	 * 查询需要导出的数据
	 * @param contact 联系人
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<ContactBean> getContacts(Contact contact){
		StringBuilder hql = new StringBuilder();
		hql.append("select new cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean(c) from Contact c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		/** 添加查询条件 */
		if (contact != null){
			if (contact.getContactGroup() != null && contact.getContactGroup().getId() != null
					&& contact.getContactGroup().getId() > 0){
				hql.append(" and c.contactGroup.id = ?");
				params.add(contact.getContactGroup().getId());
			}
		}
		hql.append(" order by c.id asc");
		return (List<ContactBean>)this.find(hql.toString(), params.toArray());
	}
}
