package cn.hnhy.hyoa.admin.addressbook.action;

import java.util.List;

import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;


/**
 * 联系组控制器
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-25 上午11:07:57
 * @version 1.0
 */
public class ContactGroupAction extends AddressbookAction {
	
	private static final long serialVersionUID = 2113480479938986716L;
	private ContactGroup contactGroup;
	private List<ContactGroup> contactGroups;
	private String ids;

	/** 分页查询联系组 */
	public String selectContactGroup(){
		try{
			contactGroups = addressbookService.getContactGroupByPage(pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加联系组 */
	public String addContactGroup(){
		try{
			addressbookService.saveContactGroup(contactGroup);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到修改联系组页面 */
	public String showUpdateContactGroup(){
		try{
			contactGroup = addressbookService.getContactGroup(contactGroup.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改联系组 */
	public String updateContactGroup(){
		try{
			addressbookService.updateContactGroup(contactGroup);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除联系组 */
	public String deleteContactGroup(){
		try{
			addressbookService.deleteContactGroup(ids.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	

	/** setter and getter method */
	public ContactGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(ContactGroup contactGroup) {
		this.contactGroup = contactGroup;
	}

	public List<ContactGroup> getContactGroups() {
		return contactGroups;
	}

	public void setContactGroups(List<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}