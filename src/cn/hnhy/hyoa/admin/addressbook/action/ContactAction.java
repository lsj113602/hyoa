package cn.hnhy.hyoa.admin.addressbook.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.hnhy.hyoa.admin.addressbook.entity.Contact;
import cn.hnhy.hyoa.admin.addressbook.entity.ContactGroup;
import cn.hnhy.hyoa.admin.addressbook.pojo.ContactBean;
import cn.hnhy.hyoa.core.common.excel.ExcelUtils;


/**
 * 联系人控制器
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-25 下午2:57:04
 * @version 1.0
 */
public class ContactAction extends AddressbookAction {
	
	private static final long serialVersionUID = 2568905512186838180L;
	private Contact contact;
	private List<Contact> contacts;
	private String ids;
	/** 定义文件上传属性 */
	private File excel;

	/** 分页查询联系人 */
	public String selectContact(){
		try{
			contacts = addressbookService.getContactByPage(contact, pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 添加联系人 */
	public String addContact(){
		try{
			addressbookService.saveContact(contact);
			ContactGroup contactGroup = contact.getContactGroup();
			this.contact = new Contact();
			contact.setContactGroup(contactGroup);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 跳转到修改联系人的页面 */
	public String showUpdateContact(){
		try{
			contact = addressbookService.getContact(contact.getId());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 修改联系人 */
	public String updateContact(){
		try{
			addressbookService.updateContact(contact);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 删除联系人 */
	public String deleteContact(){
		try{
			addressbookService.deleteContact(ids.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 联系人信息导出Excel */
	public String exportContact(){
		try{
			/** 查询需要导出的数据 */
			List<ContactBean> data = addressbookService.getContacts(contact);
			/** 定义标题行 */
			String[] titles = {"编号","姓名","性别","手机号码","邮箱", "QQ号码","生日","组名"};
			/** 把查询到得数据写入Excel */
			ExcelUtils.exportExcel("联系人信息", "通讯录", titles, data, 
					ServletActionContext.getResponse(), ServletActionContext.getRequest());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return NONE;
	}
	
	/** 联系人信息导入Excel */
	public String importExcel(){
		try{
			/** 读取上传的Excel中的数据 */
			List<List<Object>> excelData = ExcelUtils.readExcel(excel);
			/** 调用业务接口方法把Excel中的数据保存到数据库 */
			addressbookService.saveExcel(contact.getContactGroup(), excelData);
			setTip("导入成功！");
		}catch(Exception ex){
			setTip("导入失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}
}