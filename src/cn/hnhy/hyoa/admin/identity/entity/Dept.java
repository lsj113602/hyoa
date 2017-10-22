package cn.hnhy.hyoa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 部门实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:21:16
 */
@Entity @Table(name="OA_ID_DEPT")
public class Dept implements Serializable{

	private static final long serialVersionUID = -3028989788326980904L;
	/** ID	NUMBER	编号	PK主键自增长*/
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)// 自增长
	@Column(name="ID")
	private Long id;
	/** NAME	VARCHAR2(50) 部门名称	*/
	@Column(name="NAME", length=50)
	private String name;
	/** REMARK	VARCHAR2(500)	备注	*/
	@Column(name="REMARK", length=500)
	private String remark;
	/** 
	 * MODIFIER	VARCHAR2(50) 修改人 FK(OA_ID_USER)
	 * 部门修改人与用户存在N-1关系
	 **/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	/** 生成外键列 */
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID")
	private User modifier;
	/** MODIFY_DATE	DATE	修改时间	*/
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	/** 
	 * CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER) 
	 * 部门创建人与用户存在N-1关系
	 */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID")
	private User creater;
	/** CREATE_DATE	DATE 创建时间	*/
	@Column(name="CREATE_DATE")
	private Date createDate;
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}