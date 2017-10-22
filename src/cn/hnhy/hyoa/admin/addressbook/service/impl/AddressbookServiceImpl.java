package cn.hnhy.hyoa.admin.addressbook.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hnhy.hyoa.admin.addressbook.dao.ContactDao;
import cn.hnhy.hyoa.admin.addressbook.dao.ContactGroupDao;
import cn.hnhy.hyoa.admin.addressbook.entity.Contact;
import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean;
import cn.hnhy.hyoa.admin.addressbook.service.AddressbookService;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.exception.OAException;


/**
 * 通讯录业务处理接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午6:47:30
 */
public class AddressbookServiceImpl implements AddressbookService {
	/** 组合该模块所有的数据访问接口，实现该模块所有的业务处理 */
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private ContactGroupDao contactGroupDao;
	/**
	 * 分页查询联系组
	 * @param pageModel 分页实体
	 * @return 联系组集合
	 */
	public List<ContactGroup> getContactGroupByPage(PageModel pageModel){
		try{
			return contactGroupDao.getContactGroupByPage(pageModel);
		}catch(Exception ex){
			throw new OAException("分页查询联系组时出现了异常！", ex);
		}
	}
	/**
	 * 添加联系组
	 * @param contactGroup 联系组
	 */
	public void saveContactGroup(ContactGroup contactGroup){
		try{
			contactGroupDao.save(contactGroup);
		}catch(Exception ex){
			throw new OAException("添加联系组时出现了异常！", ex);
		}
	}
	/**
	 * 根据主键id获取联系组
	 * @param id 主键id
	 * @return 联系组
	 */
	public ContactGroup getContactGroup(Long id){
		try{
			return contactGroupDao.get(ContactGroup.class, id);
		}catch(Exception ex){
			throw new OAException("根据主键id获取联系组时出现了异常！", ex);
		}
	}
	/**
	 * 修改联系组
	 * @param contactGroup 联系组
	 */
	public void updateContactGroup(ContactGroup contactGroup){
		try{
			contactGroupDao.update(contactGroup);
		}catch(Exception ex){
			throw new OAException("修改联系组时出现了异常！", ex);
		}
	}
	/**
	 * 删除联系组
	 * @param ids
	 */
	public void deleteContactGroup(String[] ids){
		try{
			/** 因为配置了级联删除 cascade=CascadeType.REMOVE */
			for (String id : ids){
				contactGroupDao.delete(getContactGroup(Long.valueOf(id)));
			}
		}catch(Exception ex){
			throw new OAException("修改联系组时出现了异常！", ex);
		}
	}
	/**
	 * 加载联系组
	 * @return List
	 */
	public List<Map<String, Object>> loadContactGroupTree(){
		try{
			// data: [{id : '', pid: '', name : ''},{},...]
			List<Map<String, Object>> contactGroupLists = contactGroupDao.getContactGroupByIdAndName();
			for (Map<String,Object> map : contactGroupLists){
				map.put("pid", "0");
			}
			return contactGroupLists;
		}catch(Exception ex){
			throw new OAException("加载联系组时出现了异常！", ex);
		}
	}
	/**
	 * 分页查询联系人
	 * @param contact 联系人实体
	 * @param pageModel 分页实体
	 * @return 联系人集合
	 */
	public List<Contact> getContactByPage(Contact contact, PageModel pageModel){
		try{
			List<Contact> contacts = contactDao.getContactByPage(contact, pageModel);
			for (Contact c : contacts){
				if (c.getContactGroup() != null) c.getContactGroup().getName();
			}
			return contacts;
		}catch(Exception ex){
			throw new OAException("分页查询联系人时出现了异常！", ex);
		}
	}
	/**
	 * 添加联系人 
	 * @param contact 联系人实体
	 */
	public void saveContact(Contact contact){
		try{
			contactDao.save(contact);
		}catch(Exception ex){
			throw new OAException("添加联系人时出现了异常！", ex);
		}
	}
	/**
	 * 根据主键id查询联系人
	 * @param id
	 * @return
	 */
	public Contact getContact(Long id){
		try{
			Contact contact =  contactDao.get(Contact.class, id);
			if (contact != null){
				if (contact.getContactGroup() != null) contact.getContactGroup().getId();
			}
			return contact;
		}catch(Exception ex){
			throw new OAException("根据主键id查询联系人时出现了异常！", ex);
		}
	}
	/**
	 * 修改联系人
	 * @param contact
	 */
	public void updateContact(Contact contact){
		try{
			contactDao.update(contact);
		}catch(Exception ex){
			throw new OAException("修改联系人时出现了异常！", ex);
		}
	}
	/**
	 * 删除联系人
	 * @param ids
	 */
	public void deleteContact(String[] ids){
		try{
			contactDao.deleteContact(ids);
		}catch(Exception ex){
			throw new OAException("删除联系人时出现了异常！", ex);
		}
	}
	/**
	 * 查询需要导出的数据
	 * @param contact 联系人
	 * @return List
	 */
	public List<ContactBean> getContacts(Contact contact){
		try{
			return contactDao.getContacts(contact);
		}catch(Exception ex){
			throw new OAException("查询需要导出的数据时出现了异常！", ex);
		}
	}
	/**
	 * 把Excel中数据保存到数据库
	 * @param contactGroup
	 * @param excelData
	 */
	public void saveExcel(ContactGroup contactGroup, List<List<Object>> excelData){
		try{
			/** 循环Excel中的数据 */
			for (List<Object> rowData : excelData){
				// rowData --> Contact
				Contact contact = new Contact();
				contact.setName(rowData.get(0).toString());
				contact.setSex(rowData.get(1).toString().equals("男") ? (short)1 : (short)2);
				contact.setPhone(rowData.get(2).toString());
				contact.setEmail(rowData.get(3).toString());
				contact.setQqNum(rowData.get(4).toString());
				contact.setBirthday((Date)rowData.get(5));
				contact.setContactGroup(contactGroup);
				saveContact(contact);
			}
			
		}catch(Exception ex){
			throw new OAException("把Excel中数据保存到数据库时出现了异常！", ex);
		}
	}
}

