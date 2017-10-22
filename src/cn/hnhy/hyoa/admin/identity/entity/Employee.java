package cn.hnhy.hyoa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.hnhy.hyoa.admin.identity.entity.Dept;
import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.admin.identity.entity.User;

/**
 * 员工实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:22:33
 */
@Entity @Table(name="OA_ID_EMPLOYEE")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 4881659430015078978L;
	/** USER_ID	VARCHAR2(50)	用户ID	PK，大小写英文和数字*/
	@Id @Column(name="EMPLOYEE_ID")@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long employeeId;
	/** NAME	VARCHAR2(50)	姓名	*/
	@Column(name="NAME", length=50)
	private String name;
	/** SEX	NUMBER	性别	1:男 2:女 */
	@Column(name="SEX")
	private Short sex = 1;
	/** DEPT_ID	NUMBER	部门	FK(OA_ID_DEPT)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Dept.class)
	@JoinColumn(name="DEPT_ID", referencedColumnName="ID", 
			foreignKey=@ForeignKey(name="FK_USER_DEPT")) // 更改外键约束名
	private Dept dept;
	/** JOB_CODE	VARCHAR2(100)	职位	FK(OA_ID_JOB)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Job.class)
	@JoinColumn(name="JOB_CODE", referencedColumnName="CODE",
			foreignKey=@ForeignKey(name="FK_USER_JOB")) // 更改外键约束名
	private Job job;
	/** EMAIL	VARCHAR2(50)	邮箱	*/
	@Column(name="EMAIL", length=50)
	private String email;
	/** PHONE	VARCHAR2(50)	手机号码	*/
	@Column(name="PHONE", length=50)
	private String phone;
	/** IDENTITY	VARCHAR2(50)	政治身份	*/
	@Column(name="IDENTITY", length=50)
	private String identity;
	/** ADDRESS	VARCHAR2(50)	地址	*/
	@Column(name="ADDRESS", length=80)
	private String address;
	/** JOINDATE	VARCHAR2(50)	入职时间	*/
	@Column(name="JOINDATE", length=50)
	private Date joinDate;
	/** STATUS	NUMBER	状态	0在职,1离职 */
	@Column(name="STATUS")
	private Short status = 0;
	/** IDCARD	VARCHAR2(20)	身份证 */
	@Column(name="IDCARD",length=20)
	private String idcard ;
	/** CREATEDATE	VARCHAR2(50)	入职时间	*/
	@Column(name="CREATEDATE", length=50)
	private Date createDate;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof User)){
			return false;
		}
		User u = (User)obj;
		return u.getUserId().equals(this.employeeId);
	}
	@Override
	public int hashCode() {
		return this.employeeId == null ? 0x110 : this.employeeId.hashCode(); 
	}
	/** setter and getter method */
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	
}