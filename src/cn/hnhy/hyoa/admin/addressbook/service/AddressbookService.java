package cn.hnhy.hyoa.admin.addressbook.service;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.addressbook.entity.Contact;
import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean;
import cn.hnhy.hyoa.core.common.web.PageModel;


/**
 * 通讯录业务处理接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午6:48:25
 */
public interface AddressbookService {
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return 联系组集合
	 */
	List<ContactGroup> getContactGroupByPage(PageModel pageModel);
	/**
	 * 添加联系组
	 * @param contactGroup 联系组
	 */
	void saveContactGroup(ContactGroup contactGroup);
	/**
	 * 根据主键id获取联系组
	 * @param id 主键id
	 * @return 联系组
	 */
	ContactGroup getContactGroup(Long id);
	/**
	 * 修改联系组
	 * @param contactGroup 联系组
	 */
	void updateContactGroup(ContactGroup contactGroup);
	/**
	 * 删除联系组
	 * @param ids
	 */
	void deleteContactGroup(String[] ids);
	/**
	 * 加载联系组
	 * @return List
	 */
	List<Map<String, Object>> loadContactGroupTree();
	/**
	 * 分页查询联系人
	 * @param contact 联系人实体
	 * @param pageModel 分页实体
	 * @return 联系人集合
	 */
	List<Contact> getContactByPage(Contact contact, PageModel pageModel);
	/**
	 * 添加联系人 
	 * @param contact 联系人实体
	 */
	void saveContact(Contact contact);
	/**
	 * 根据主键id查询联系人
	 * @param id
	 * @return
	 */
	Contact getContact(Long id);
	/**
	 * 修改联系人
	 * @param contact
	 */
	void updateContact(Contact contact);
	/**
	 *  删除联系人
	 * @param ids
	 */
	void deleteContact(String[] ids);
	/**
	 * 查询需要导出的数据
	 * @param contact 联系人
	 * @return List
	 */
	List<ContactBean> getContacts(Contact contact);
	/**
	 * 把Excel中数据保存到数据库
	 * @param contactGroup
	 * @param excelData
	 */
	void saveExcel(ContactGroup contactGroup, List<List<Object>> excelData);
	
}
