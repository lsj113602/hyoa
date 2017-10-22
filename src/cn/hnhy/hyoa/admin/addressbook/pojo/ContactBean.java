package cn.hnhy.hyoa.admin.addressbook.pojo;

import java.text.SimpleDateFormat;

import cn.hnhy.hyoa.admin.addressbook.entity.Contact;



/**
 * 联系人
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月4日 下午2:09:08
 */
public class ContactBean {
	
	private Long id;
	private String name;
	private String sex;
	private String phone;
	private String email;
	private String qqNum;
	private String birthday;
	private String groupName;
	
	public ContactBean() {
	}
	public ContactBean(Contact contact) {
		this.id = contact.getId();
		this.name = contact.getName();
		this.sex = contact.getSex() == 1 ? "男" : "女";
		this.phone = contact.getPhone();
		this.email = contact.getEmail();
		this.qqNum = contact.getQqNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.birthday = sdf.format(contact.getBirthday());
		this.groupName = contact.getContactGroup().getName();
	}
	/** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex == 1 ? "男" : "女";
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}