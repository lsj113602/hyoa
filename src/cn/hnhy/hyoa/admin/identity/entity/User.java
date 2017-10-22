package cn.hnhy.hyoa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 * 用户实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:22:33
 */
@Entity @Table(name="OA_ID_USER")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable{
	
	private static final long serialVersionUID = 4881659430015078978L;
	/** USER_ID	VARCHAR2(50)	用户ID	PK，大小写英文和数字*/
	@Id @Column(name="USER_ID", length=50)
	private String userId;
	/** PASS_WORD	VARCHAR2(50)	密码	MD5加密*/
	@Column(name="PASS_WORD", length=50)
	private String passWord;
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
	/** TEL	VARCHAR2(50)	电话号码	*/
	@Column(name="TEL", length=50)
	private String tel;
	/** PHONE	VARCHAR2(50)	手机号码	*/
	@Column(name="PHONE", length=50)
	private String phone;
	/** QQ_NUM	VARCHAR2(50)	QQ号码	*/
	@Column(name="QQ_NUM", length=50)
	private String qqNum;
	/** QUESTION	NUMBER	问题编号	*/
	@Column(name="QUESTION")
	private Short question;
	/** ANSWER	VARCHAR2(200) 回答结果	*/
	@Column(name="ANSWER", length=200)
	private String answer;
	/** STATUS	NUMBER	状态	0新建,1审核,2不通过审核,3冻结 */
	@Column(name="STATUS")
	private Short status = 0;
	/** CREATE_DATE	DATE	创建时间	*/
	@Column(name="CREATE_DATE")
	private Date createDate;
	/** CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID")
	private User creater;
	/** MODIFIER	VARCHAR2(50)	修改人	FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID")
	private User modifier;
	/** MODIFY_DATE	DATE	修改时间	*/
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	/** CHECKER	VARCHAR2(50)	审核人	FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CHECKER", referencedColumnName="USER_ID")
	private User checker;
	/** CHECK_DATE	DATE	审核时间	*/
	@Column(name="CHECK_DATE")
	private Date checkDate;
	/** 用户与角色存在N-N关联 mappedBy:由哪边维护中间表 */
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Role.class, mappedBy="users")
	private Set<Role> roles = new HashSet<>();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof User)){
			return false;
		}
		User u = (User)obj;
		return u.getUserId().equals(this.userId);
	}
	@Override
	public int hashCode() {
		return this.userId == null ? 0x110 : this.userId.hashCode(); 
	}
	
	/** setter and getter method */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public Short getQuestion() {
		return question;
	}
	public void setQuestion(Short question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public User getModifier() {
		return modifier;
	}
	public void setModifier(User modifier) {
		this.modifier = modifier;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public User getChecker() {
		return checker;
	}
	public void setChecker(User checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}