package cn.hnhy.hyoa.admin.addressbook.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.addressbook.entity.Contact;
import cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;


/**
 * 联系人数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午7:54:38
 */
public interface ContactDao extends HibernateDao {
	/**
	 * 分页查询联系人
	 * @param contact 联系人实体
	 * @param pageModel 分页实体
	 * @return 联系人集合
	 */
	List<Contact> getContactByPage(Contact contact, PageModel pageModel);
	/**
	 * 删除联系人
	 * @param ids
	 */
	void deleteContact(String[] ids);
	/**
	 * 查询需要导出的数据
	 * @param contact 联系人
	 * @return List
	 */
	List<ContactBean> getContacts(Contact contact);
	
}
